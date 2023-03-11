//import com.google.gson.Gson;
//import com.qiniu.common.QiniuException;
//import com.qiniu.common.Zone;
//import com.qiniu.http.Response;
//import com.qiniu.storage.BucketManager;
//import com.qiniu.storage.Configuration;
//import com.qiniu.storage.UploadManager;
//import com.qiniu.storage.model.DefaultPutRet;
//import com.qiniu.util.Auth;
//import org.junit.Test;
//
///**
// * @author 周书胜
// * @date 2023年03月06 16:39
// */
//public class TestQiniu {
//
//    // 上传本地文件
//    @Test
//    public void testUpload() {
//        //构造一个带指定Zone对象的配置类
//        Configuration cfg = new Configuration(Zone.zone2());
//        //...其他参数参考类注释
//        UploadManager uploadManager = new UploadManager(cfg);
//        //...生成上传凭证，然后准备上传
//        String accessKey = "DeQb8F7rtYtg8oWsGZnnSg9R2lPFLoLxPpn_2ygj";
//        String secretKey = "zmGXODArxINWP5lWRn--IWTdZixU1gLU2MzWmnGP";
//        String bucket = "shf-image-source";
//        //如果是Windows情况下，格式是 D:\\qiniu\\test.png，可支持中文
//        String localFilePath = "D:\\imageSource\\th.jpg";
//        //默认不指定key的情况下，以文件内容的hash值作为文件名
//        String key = null;
//        Auth auth = Auth.create(accessKey, secretKey);
//        String upToken = auth.uploadToken(bucket);
//        try {
//            Response response = uploadManager.put(localFilePath, key, upToken);
//            //解析上传成功的结果
//            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
//            System.out.println(putRet.key);
//            System.out.println(putRet.hash);
//        } catch (QiniuException ex) {
//            Response r = ex.response;
//            System.err.println(r.toString());
//            try {
//                System.err.println(r.bodyString());
//            } catch (QiniuException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    // 删除指定的文件
//    @Test
//    public void testDelete() {
//        //构造一个带指定Zone对象的配置类
//        Configuration cfg = new Configuration(Zone.zone2());
//        //...其他参数参考类注释
//        String accessKey = "DeQb8F7rtYtg8oWsGZnnSg9R2lPFLoLxPpn_2ygj";
//        String secretKey = "zmGXODArxINWP5lWRn--IWTdZixU1gLU2MzWmnGP";
//        String bucket = "shf-image-source";
//        String key = "FmvFFGYOAtu28kyfZq-tV7KetE1T";//文件名称
//        Auth auth = Auth.create(accessKey, secretKey);
//        BucketManager bucketManager = new BucketManager(auth, cfg);
//        try {
//            bucketManager.delete(bucket, key);
//        } catch (QiniuException ex) {
//            //如果遇到异常，说明删除失败
//            System.err.println(ex.code());
//            System.err.println(ex.response.toString());
//        }
//    }
//}
