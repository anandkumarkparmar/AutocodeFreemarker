package app;

import utils.AutocodeFreemarker;

/**
 * Created by anandparmar on 04/10/17.
 */
public class DemoApplication{

    public static void main(String[] args){
        AutocodeFreemarker autocode = new AutocodeFreemarker();

        autocode.create("demo_template.ftl", "demo_datamodel.json", "temp/chemp/demo_output.html");
    }
}
