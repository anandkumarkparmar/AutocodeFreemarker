package utils;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import config.PathConfig;
import entities.DemoDataModelEntity;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;

public class AutocodeFreemarker{

	/**
	 * 	path of project in local machine
     */
	private String PATH_PROJECT;

	/**
	 * 	path of source folder
     */
	private String PATH_SOURCE_ROOT;

	/**
	 * 	path of path.config(which contains all source folder paths) file in local machine
     */
	private String PATH_CONFIG_PATH;

	/**
	 *	path of datamodels(JSON files, which are used as input value of variables)
	 */
	private String PATH_DATA_MODEL;

	/**
	 * 	path of entities(JAVA files, which are equivalent to the datamodel)
     */
	private String PATH_ENTITIES;

	/**
	 * 	path of templates(FTL files, which will be converted in to output files using datamodels)
     */
	private String PATH_TEMPLATES;

	private Configuration configuration;

	/**
	 * 	base path of generated files
     */
	private String PATH_AUTOCODE_GENERATED;

	public String getPathProject() {
		return PATH_PROJECT;
	}

	public void setPathProject(String pathProject) {
		PATH_PROJECT = pathProject;
	}

	public String getPathConfigPath() {
		return PATH_CONFIG_PATH;
	}

	public void setPathConfigPath(String pathConfigPath) {
		PATH_CONFIG_PATH = pathConfigPath;
	}

	public String getPathSourceRoot() {
		return PATH_SOURCE_ROOT;
	}

	public void setPathSourceRoot(String pathSourceRoot) {
		PATH_SOURCE_ROOT = pathSourceRoot;
	}

	public String getPathDataModel() {
		return PATH_DATA_MODEL;
	}

	public void setPathDataModel(String pathDataModel) {
		PATH_DATA_MODEL = pathDataModel;
	}

	public String getPathEntities() {
		return PATH_ENTITIES;
	}

	public void setPathEntities(String pathEntities) {
		PATH_ENTITIES = pathEntities;
	}

	public String getPathTemplates() {
		return PATH_TEMPLATES;
	}

	public void setPathTemplates(String pathTemplates) {
		PATH_TEMPLATES = pathTemplates;
	}

	public String getPathAutocodeGenerated() {
		return PATH_AUTOCODE_GENERATED;
	}

	public void setPathAutocodeGenerated(String pathAutocodeGenerated) {
		PATH_AUTOCODE_GENERATED = pathAutocodeGenerated;
	}

	public Configuration getConfiguration() {
		if(configuration == null) {
			configuration = new Configuration(Configuration.VERSION_2_3_23);
			try {
				configuration.setDirectoryForTemplateLoading(new File(PATH_TEMPLATES));
			} catch (IOException e) {
				e.printStackTrace();
			}

			configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
			configuration.setLogTemplateExceptions(false);
			return configuration;
		} else {
			return configuration;
		}
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public AutocodeFreemarker() {
		initPaths("src/main/java", "config/path_config.json");
		refreshAutocodeGeneratedDirectory();
	}

	/**
	 *
     */
	private void refreshAutocodeGeneratedDirectory() {
		File rootFile = new File(PATH_AUTOCODE_GENERATED);
		deleteFile(rootFile);
		rootFile.mkdirs();
	}

	private void deleteFile(File fileOrDirectory) {
		if (fileOrDirectory.exists() && fileOrDirectory.isDirectory()) {
			for (File child : fileOrDirectory.listFiles()) {
				deleteFile(child);
			}
		}
		if (fileOrDirectory.exists()) {
			fileOrDirectory.delete();
		}
	}

	/**
	 * 	function: getPlatformIndependentPath
	 * 		convert platform independent path using File.separator
	 *
	 * @param basePath base folder path
	 * @param relativePath path of file or folder which we want to convert to platform independent
     * @return path as a String which will be platform independent
     */
	public String getPlatformIndependentPath(String basePath, String relativePath){
		String output = basePath;

		String[] relativePathArray = relativePath.split("/");
		for (String relativePathDir: relativePathArray) {
			output = output + File.separator + relativePathDir;
		}

		return output;
	}

	/**
	 * 	function: initPaths
	 * 		Initialize the paths which will be used to run the applications
	 *
	 * 	@param sourceFolderRelativePath path of source folder
	 *	@param pathConfigRelativePath path of path_config.json file
	 *
     */
	public void initPaths(String sourceFolderRelativePath, String pathConfigRelativePath){
		setPathProject(new File("").getAbsolutePath());
		setPathSourceRoot(getPlatformIndependentPath(getPathProject(), sourceFolderRelativePath));
		setPathConfigPath(getPlatformIndependentPath(getPathSourceRoot(), pathConfigRelativePath));

		try {
			JsonReader reader = new JsonReader(new FileReader(PATH_CONFIG_PATH));
			PathConfig pathConfig = new Gson().fromJson(reader, PathConfig.class);

			setPathDataModel(getPlatformIndependentPath(getPathSourceRoot(), pathConfig.getDataModelsPath()));
			setPathEntities(getPlatformIndependentPath(getPathSourceRoot(), pathConfig.getEntitiesPath()));
			setPathTemplates(getPlatformIndependentPath(getPathSourceRoot(), pathConfig.getTemplatePath()));
			setPathAutocodeGenerated(getPlatformIndependentPath(getPathProject(), pathConfig.getGeneratedFilesPath()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}


	public void create(String templateName, String dataModelName, String outputFileName) {
		try {
			Template template = getConfiguration().getTemplate(templateName);

			JsonReader reader = new JsonReader(new FileReader(PATH_DATA_MODEL + File.separator + dataModelName));
			DemoDataModelEntity entity = new Gson().fromJson(reader, DemoDataModelEntity.class);

			File outputFile = new File(PATH_AUTOCODE_GENERATED + File.separator + outputFileName);
			outputFile.getParentFile().mkdirs();

			Writer writer = new FileWriter(outputFile);

			template.process(entity, writer);
		} catch (IOException | TemplateException e) {
			e.printStackTrace();
		}
	}
}
