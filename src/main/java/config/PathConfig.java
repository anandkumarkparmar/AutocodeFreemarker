package config;

public class PathConfig{
    private String dataModelsPath;
    private String templatePath;
    private String entitiesPath;
    private String generatedFilesPath;

    public String getDataModelsPath() {
        return dataModelsPath;
    }

    public void setDataModelsPath(String dataModelsPath) {
        this.dataModelsPath = dataModelsPath;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public String getEntitiesPath() {
        return entitiesPath;
    }

    public void setEntitiesPath(String entitiesPath) {
        this.entitiesPath = entitiesPath;
    }

    public String getGeneratedFilesPath() {
        return generatedFilesPath;
    }

    public void setGeneratedFilesPath(String generatedFilesPath) {
        this.generatedFilesPath = generatedFilesPath;
    }
}