package dev.selenium.factory;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "file:${user.dir}/src/test/resources/config/dev.config.properties",
        "file:${user.dir}/src/test/resources/config/prod.config.properties",
        "file:${user.dir}/src/test/resources/config/staging.config.properties"
})
public interface IConfigFactory extends Config {

    // RECOMMENDED: Always pass the value for env() from system properties or environment variables, and never from .properties file
    // Assigning it to a default value, in-case we do not get any values from the given recommended ways
    @DefaultValue("prod")
    String env();

    @Key("${env}.browser")
    String browser();

    @Key("${env}.headless")
    boolean headless();

    @Key("${env}.incognito")
    boolean incognito();

    @Key("${env}.closeBrowser")
    boolean closeBrowser();
}
