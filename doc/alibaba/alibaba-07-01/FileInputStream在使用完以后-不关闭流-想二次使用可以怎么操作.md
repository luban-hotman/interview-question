```java
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * FileInputStream在使用完以后，不关闭流，想二次使用可以怎么操作？
 */
public class FileInputStreamDemo {

    public static void main(String[] args) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        String parentPath = "";
        String text1Path = parentPath + "text1.txt";
        String text2Path = parentPath + "text2.txt";
        String text3Path = parentPath + "text3.txt";

        FileInputStream text1InputStream = new FileInputStream(text1Path);
        FileOutputStream text2OutputStream = new FileOutputStream(text2Path);
        FileOutputStream text3OutputStream = new FileOutputStream(text3Path);

        int len;
        byte[] bytes = new byte[1024];
        while ((len = text1InputStream.read(bytes)) != -1) {
            text2OutputStream.write(bytes, 0, len);
        }
        Class<? extends FileInputStream> inputStreamClass = text1InputStream.getClass();
        Field fd = inputStreamClass.getDeclaredField("fd");
        fd.setAccessible(true);
        Object o = fd.get(text1InputStream);
        System.out.println(o.hashCode());

        if (text1InputStream.read() == -1) {
            Method open0 = inputStreamClass.getDeclaredMethod("open0", String.class);
            open0.setAccessible(true);
            open0.invoke(text1InputStream, text1Path);
        }
        Object o1 = fd.get(text1InputStream);
        System.out.println(o1.hashCode());

        while ((len = text1InputStream.read(bytes)) != -1) {
            text3OutputStream.write(bytes, 0, len);
        }

        text2OutputStream.flush();
        text2OutputStream.close();

        text3OutputStream.flush();
        text3OutputStream.close();
    }

}


```

## open和read执行流程

![](http://pu1tsaa10.bkt.clouddn.com//image/png/FileInputStream%E5%8E%9F%E7%90%86%E5%9B%BE.png)

## 总结

FileInputStream通过再次open能实现重读的原理是：底层代码重新打开了一次文件，返回文件描述符(Linux)或句柄，这个值存在FileInputStream中的一个类型为FileDescriptor的对象中，FileDescriptor的fd是文件描述符，handle是句柄

