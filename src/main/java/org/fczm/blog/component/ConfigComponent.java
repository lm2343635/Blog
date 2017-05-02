package org.fczm.blog.component;

import org.springframework.stereotype.Component;

@Component
public class ConfigComponent {

    // Default file upload folder.
    public String UploadFolder = "/files";

    // Picture format.
    public String ImageFormat = ".jpg";

    // Limitation of uploaded file.
    public int FileMaxSize = 512 * 1024 * 1024;

    // Max image width.
    public int MaxImageWidth = 1440;

    // Dynamic config path.
    public String ConfigPath = "/WEB-INF/config.json";

    public String rootPath;

    public ConfigComponent() {
        rootPath = this.getClass().getClassLoader().getResource("/").getPath().split("WEB-INF")[0];
        load();
    }

    public void load() {

    }

}
