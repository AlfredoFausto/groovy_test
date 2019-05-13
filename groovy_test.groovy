@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7' )
import groovy.util.CliBuilder
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.POST
import static groovyx.net.http.ContentType.JSON
import static groovyx.net.http.ContentType.URLENC
import groovy.json.*

//parse arguments
def cli = new CliBuilder(usage: 'servicenow-integration_update.groovy [options]', header: 'Options;')

cli.user(args:1, argName: 'user', required: true, 'user credentials') 
cli.pwd(args:1, argName: 'pwd', required: true, 'password')
cli.sysIdValue(args:1, argName: 'sysIdValue', required: true, 'incident sys_id')
cli.workNoteValue(args:1, argName: 'workNoteValue', required: false, 'work note in incident')
cli.stateIncidentValue(args:1, argName: 'stateIncidentValue', required: false, 'incident state')
cli.close_incident(args:1, argName: 'close_incident', required: false, 'select if incident can be closed')

def options = cli.parse(args)

def user = options.user
def pwd = options.pwd
def sysIdValue = options.sysIdValue
def workNoteValue = options.workNoteValue
def stateIncidentValue = options.stateIncidentValue
def close_incident = options.close_incident

//Create json and auth
def authString = user + ":" + pwd
def post = new URL("https://youserver.com").openConnection();
def data = ""

//send request, auth and content type
post.setRequestMethod("PUT")
post.setDoOutput(true)
post.setRequestProperty("Authorization", "Basic ${authString.bytes.encodeBase64().toString()}")
post.setRequestProperty("Content-Type", "application/json")
post.addRequestProperty("Accept", "application/json")
post.getOutputStream().write(data.getBytes("UTF-8"));

//get response message that was updated
println post.getResponseMessage()

//get response code and the response from request with a json
def postRC = post.getResponseCode();
def response = post.inputStream.text

println(postRC)
// if is 200 ok, we should get the response
if(postRC.equals(200)) {
  println response 
}

//Parse response
def jsonSlurper = new JsonSlurper()
def object = jsonSlurper.parseText(response)
 
//get the value from the json object 
def sysIdValue = object.result.sys_id