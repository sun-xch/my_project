package com.sso.commons.jwt;

import io.jsonwebtoken.*;
import org.codehaus.jackson.map.ObjectMapper;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.util.Date;

public class JWTUtils {

    //服务器的key，用于加密的key数据。如果可以使用客户端生成的key,当前定义常量可以不用
    private static final String JWT_SECERT = "test_jwt_secert";
    //java对象 和 json字符串的双向转换
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static final int JWT_ERRCODE_EXPIRE = 1005;  //Token过期

    public static final int JWT_ERRCODE_FAIL = 1006;    //验证不通过

    public static SecretKey generalKey(){
        try {
            /**
             * byte[] encodeKey = Base64.decode(JWT_SECERT);
             * 不管哪种方式最终得到一个byte[]类型的key就行
             */
            byte[] encodeKey = JWT_SECERT.getBytes("UTF-8");
            //AES加密方式
            SecretKey key = new SecretKeySpec(encodeKey,0,encodeKey.length,"AES");
            return key;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 签发JWT, 创建token的方法
     * @param id        jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击
     * @param iss       jwt签发者
     * @param subject   jwt所面向的用户。 payload中记录的公开信息(public claims) 当前环境中就是用户登录名
     * @param ttlMillis 有效期，单位毫秒
     * @return token    token是一次性的，是为一个用户的有效登录周期准备的一个token，用户退出或超时，token失效。
     */
    public static String createJWT(String id, String iss, String subject, long ttlMillis){
        //加密算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //当前时间
        Long nowMillis = System.currentTimeMillis();
        //当前时间日期对象
        Date now = new Date(nowMillis);
        //获取密匙对象
        SecretKey secretKey = generalKey();
        //创建jwt的构建器 就是使用指定的信息和加密算法，生成token的工具
        JwtBuilder builder = Jwts.builder()
                .setId(id)              //设置身份标志。就是一个客户端的唯一标记。如：可以使用用户的主键，客户端的IP，服务器生成的随机数据。
                .setIssuer(iss)
                .setSubject(subject)
                .setIssuedAt(now)       //token生成的时间
                .signWith(signatureAlgorithm,secretKey); //设定密匙 和 算法
        if(ttlMillis >= 0){
            long expMillis = nowMillis + ttlMillis;
            Date expDate = new Date(expMillis);     //token的失效时间
            builder.setExpiration(expDate);
        }
        return builder.compact();       //生成token
    }

    /**
     * 验证JWT
     * @param jwtstr
     * @return
     */
    public static JWTResult validateJWT(String jwtstr){
        //缺少对比 token正确性
        //在此应该与 Redis 或 数据库中 token对比认证
        JWTResult checkResult = new JWTResult();
        Claims claims = null;
        try{
            claims = parseJWT(jwtstr);
            checkResult.setSuccess(true);
            checkResult.setClaims(claims);
        } catch (ExpiredJwtException e){//超时
            checkResult.setErrCode(JWT_ERRCODE_EXPIRE);
            checkResult.setSuccess(false);
        } catch (SignatureException e){//校验失败
            checkResult.setErrCode(JWT_ERRCODE_FAIL);
            checkResult.setSuccess(false);
        } catch (Exception e){
            checkResult.setErrCode(JWT_ERRCODE_FAIL);
            checkResult.setSuccess(false);
        }
        return checkResult;
    }

    /**
     * 解析JWT字符串
     * @param jwt  就是服务器为客户端生成的签名数据，就是token
     * @return
     */
    public static Claims parseJWT(String jwt){
        SecretKey secretKey = generalKey();
        //getBody 获取的就是token中记录的payload数据，就是payload中保存的所有的claims。
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();
    }

    /**
     * 生成subject信息
     * @param subObj    要转换的对象
     * @return          java对象 --> json字符串出错时返回null;
     */
    public static String generalSubject(Object subObj){
        try{
            return MAPPER.writeValueAsString(subObj);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
