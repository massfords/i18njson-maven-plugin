import groovy.json.JsonSlurper

File dir = basedir;
def reportFile = new File( dir, "target/i18njson-maven-plugin/report.json" );
assert reportFile.isFile()
def contents = new JsonSlurper().parse(new FileReader(reportFile))
assert contents instanceof Map

assert contents.get("gftotal") == 1