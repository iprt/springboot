package org.iproute.jdk.nashorn;

import lombok.extern.slf4j.Slf4j;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.List;

/**
 * NashornMain
 *
 * @author tech@intellij.io
 */
@Slf4j
public class NashornMain {

    public static final String ENGINE_NAME = "nashorn";

    public static void main(String[] args) throws ScriptException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();

        List<ScriptEngineFactory> engineFactories = scriptEngineManager.getEngineFactories();


        engineFactories.forEach(ef -> {
            String engineName = ef.getEngineName();
            String engineVersion = ef.getEngineVersion();

            log.info("engineName : {},engineVersion :{}", engineName, engineVersion);
        });

        ScriptEngine engine = scriptEngineManager.getEngineByName(ENGINE_NAME);

        Object result = engine.eval(
                "var greeting='hello world';" +
                        "print(greeting);" +
                        "greeting");

        log.info("result: {}", result);
    }


}
