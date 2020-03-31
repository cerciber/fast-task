package fasttask.controller.code;

import fasttask.controller.settting.SettingController;
import fasttask.data.system.FileAccess;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaScriptController extends CodeController {

    static final String JAVASCRIPT_GENERATED_FILE = "src/fasttask/data/files/generated/JavaScriptGenerated/PJavaScriptGenerated.py";
    static final String JAVASCRIPT_GENERATED_DIRECTORY = "src/fasttask/data/files/generated/JavaScriptGenerated";

    public JavaScriptController(String direction) {
        super(direction);
    }

    @Override
    public String languaje() {
        return "JavaScript";
    }

    @Override
    public Color color() {
        return new Color(226, 113, 0);
    }

    @Override
    public String classNameRE() {
        return "class[ ].*?(.*?)[{]";
    }

    @Override
    public String descriptionRE() {
        return "(/[*](.*?)[*]/)|(//(.*?)\n)";
    }

    @Override
    public String parametersRE() {
        return ".*?constructor[ ]*?[(](.*?)[)]";
    }

    @Override
    public void creteExecutable(String[] parameters) {

        // Crear codigo del ejecutable
        String parametersString = Arrays.toString(parameters);
        String generatedCode = FileAccess.loadContent(direction) + "\n" + "new " + className() + "(" + parametersString.substring(1, parametersString.length() - 1) + ");";

        // Generar ejecutable
        FileAccess.savedContent(JAVASCRIPT_GENERATED_FILE, generatedCode);

    }

    @Override
    public String runCommand() {
        return "pushd " + SettingController.getJavaScriptFolder() + " "
                + "&& node \"" + new File(JAVASCRIPT_GENERATED_FILE).getAbsolutePath() + "\" ";
    }

}