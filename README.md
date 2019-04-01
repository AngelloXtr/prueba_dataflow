# FGA Back Archetype for Java Applications

An archetype to create BBVA Back Applications deployed on Google Cloud Platform (AppEngine) 
using [Java Standard Environment](https://cloud.google.com/appengine/docs/standard/java/)

## Prerequisites

These are the necessary tools to use this archetype

* [Java SDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html) (at least 1.7.0)
* [Apache Maven](https://maven.apache.org/download.cgi) (at least 3.1.0)
* [Git](https://git-scm.com/download)
* [GCloud SDK](https://cloud.google.com/sdk/)
* [Artifactory](https://platform.bbva.com/en-us/developers/engines/gcp/documentation/aditional-documentation/procedures/artifactory)

## Usage

### Git

First of all, we need to disconnect from the git archetype repository and connect with our git repository

* Showing your remote

```
git remote -v
```

* Disconnecting from git archetype repository

```
git remote rm origin
```

* Recreate local repository

```
rm -rf .git && git init
```

* Connecting to the project git repository

```
git remote add origin <your-repo>
```

### Configuration

Once that you have the archetype code, you need to customize your pom.xml with your sp
file like following:

* Artifact. You need to specify your artifact id and version

    ```xml
    <project>
      ...
      <artifactId>my-artifact</artifactId>
      <version>version</version>
      ...
    </project>
    ```

* Name and description
    ```xml
    <project>
      ...
      <name>My Application</name>
      <description>Awesome description about my application</description>
      ...
    </project>
    ```
    
* Google projects identifiers
    ```xml
    <project>
      ...
      <properties>
          ...
          <dev.appengine.app.id>dev-bbva-app-id</dev.appengine.app.id>
          <au.appengine.app.id>au-bbva-app-id</au.appengine.app.id>
          <pr.appengine.app.id>bbva-app-id</pr.appengine.app.id>
          ...
      </properties>
      ...
    </project>
    ```



### Authentication

We need to be authenticated using `gcloud`

* Authorize gcloud to access Google Cloud Platform

```
gcloud auth login
```
* Acquire new user credentials to use for Application Default Credentials

```
gcloud auth application-default login
```

### GCloud

These steps are not necessary, but it can help us to be sure that the parameters for Google Cloud Platform 
interaction are the correct

* Listing current configuration

```
gcloud config list
```

* Listing available accounts

```
gcloud auth list
```

* Changing account

```
gcloud config set account <account>
```

* Changing project

```
gcloud config set project <project>
```

### Tasks

These are the available task to perform:

* Run on local development server

```
    mvn clean appengine:run -P {profile}
```

* Deploy on Google AppEngine

```
    mvn clean appengine:deploy -P {profile}
```

The profile parameter point to desired environment configuration to use. Allowed values are:

* local (for local machine execution)
* dev.bbva.com (development environment)
* au-bbva.com (user acceptance environment)
* bbva.com (production environment)

You can find more tasks in the [App Engine Maven Plugin (Cloud SDK-based)](https://cloud.google.com/appengine/docs/standard/java/tools/maven-reference) documentation.

## REST Services registration

After create new web services, you need to register them into
```com.bbva.config.Application``` class

	
## Versioning Rules

Given a version number MAJOR.MINOR.PATCH, increment the:

* MAJOR version when you make incompatible API changes,
* MINOR version when you add functionality in a backwards-compatible manner, and
* PATCH version when you make backwards-compatible bug fixes.

Additional labels for pre-release and build metadata are available as extensions to the MAJOR.MINOR.PATCH format.
Snapshot versions should have '-SNAPSHOT' suffix.

For more information, see [SemVer](http://semver.org/)

## Change log

See [CHANGELOG](CHANGELOG.md).

## Support

For any problem or bug, please contact with BBVA Google Cloud Platform Team following this [template](https://platform.bbva.com/en-us/developers/engines/gcp/documentation/procedures/issue-support-request).