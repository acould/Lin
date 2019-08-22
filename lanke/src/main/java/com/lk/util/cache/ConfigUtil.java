package com.lk.util.cache;


import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.core.io.DefaultResourceLoader;

import com.alibaba.druid.util.StringUtils;
import com.google.common.collect.Maps;
import com.lk.util.PropertiesLoader;

/**
 * Datetime   ： 2016年1月6日 上午9:50:47<br>
 * Title      :  Global.java<br>
 * Description:  全局配置类<br>
 * Company    :  hiwan<br>
 *
 * @author cbj
 */
public class ConfigUtil {
	 /**
     * 上传文件的根目录
     */ 
	private static String PATH = "com.lk.file.path";
	
    /**
     * 当前对象实例
     */
    private static ConfigUtil global = new ConfigUtil();

    /**
     * 保存全局属性值
     */
    private static Map<String, String> map = Maps.newHashMap();

    /**
     * 属性文件加载对象
     */
    private static PropertiesLoader loader = new PropertiesLoader("classpath:dbconfig.properties");
    public static final String EMPTY = "";

    /**
     * 显示/隐藏
     */
    public static final String SHOW = "1";
    public static final String HIDE = "0";

    /**
     * 是/否
     */
    public static final String YES = "1";
    public static final String NO = "0";

    /**
     * 对/错
     */
    public static final String TRUE = "true";
    public static final String FALSE = "false";

    /**
     * 获取当前对象实例
     */
    public static ConfigUtil getInstance() {
        return global;
    }

    /**
     * 获取配置
     *
     * @see ${fns:getConfig('adminPath')}
     */
    public static String getConfig(String key) {
        String value = map.get(key);
        if (value == null) {
            value = loader.getProperty(key);
            map.put(key, value != null ? value : EMPTY);
        }
        return value;
    }


    /**
     * 在修改系统用户和角色时是否同步到Activiti
     */
	/*public static Boolean isSynActivitiIndetity() {
		String dm = getConfig("activiti.isSynActivitiIndetity");
		return "true".equals(dm) || "1".equals(dm);
	}*/

    /**
     * 页面获取常量
     *
     * @see ${fns:getConst('YES')}
     */
    public static Object getConst(String field) {
        try {
            return ConfigUtil.class.getField(field).get(null);
        } catch (Exception e) {
            // 异常代表无配置，这里什么也不做
        }
        return null;
    }

    /**
     * 获取上传文件的根目录
     *
     * @return
     */
    public static String getFilesBaseDir() {
        String dir = getConfig(PATH);

        //项目根目录
		/*if (StringUtils.isBlank(dir)){
			try {
				dir = ServletContextFactory.getServletContext().getRealPath("/");
			} catch (Exception e) {
				return "";
			}
		}
		if(!dir.endsWith("/")) {
			dir += "/";
		}
		 */
        return dir;
    }

    /**
     * 获取工程路径
     *
     * @return
     */
    public static String getProjectPath() {
        // 如果配置了工程路径，则直接返回，否则自动获取。
        String projectPath = ConfigUtil.getConfig("projectPath");
        if (StringUtils.isEmpty(projectPath)) {
            return projectPath;
        }
        try {
            File file = new DefaultResourceLoader().getResource("").getFile();
            if (file != null) {
                while (true) {
                    File f = new File(file.getPath() + File.separator + "src" + File.separator + "main");
                    if (f == null || f.exists()) {
                        break;
                    }
                    if (file.getParentFile() != null) {
                        file = file.getParentFile();
                    } else {
                        break;
                    }
                }
                projectPath = file.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return projectPath;
    }

}
