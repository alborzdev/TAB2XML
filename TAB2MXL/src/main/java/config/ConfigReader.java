package config;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

public class ConfigReader {
    //private String filepath;
    private File config;
    private ArrayList<ConfigNode> configData;
    private static ConfigReader instance = new ConfigReader("../config.ini");
    

    private ConfigReader(String filepath){
        //this.filepath = filepath;
        this.config = new File(filepath);
        this.configData = new ArrayList<ConfigNode>();
        readFile();
        //System.out.println("DEBUG: ConfigReader ready to use");
    }

    public static ConfigReader getConfig(){
        return instance;
    }

    public String getAttr(String attr){
        String out = null;
        for(ConfigNode cfgn: configData){
            if(cfgn.getName().equals(attr)){
                out = cfgn.getValue();
                break;
            }
        }
        return out;
    }    

    private void readFile(){
        try{
            Scanner sc = new Scanner(config);
            String[] temp;
            while(sc.hasNextLine()){
                temp = sc.nextLine().split(":", 2);
                configData.add(new ConfigNode(temp[0],temp[1]));
            }
            sc.close();
        }catch(Exception e){
            System.out.println("DEBUG: configuration file error");
            System.out.println(e.toString());
        }
    }

    private class ConfigNode{
        String name, value;
        public ConfigNode(String name, String value){
            this.name = name;
            this.value = value;
            //System.out.println("DEBUG: configuration node created: " +name+":"+value);
        }

        public String getName(){
            return name;
        }

        public String getValue(){
            return value;
        }
    }

}
