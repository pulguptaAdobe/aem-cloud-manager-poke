# AEM Cloud Manager Poke

A utility that reads in a JSON file in the following format:

```
{
    [
        {
            "url": "https://....",
            "path": "/content/dam.1.json",
            "interval": 1234,
            "lastInvocation": null,
            "auth": {
                "username": "myname",
                "password" : "mypassword"
            }
        },
        {
            ...
        }

    ],
    "terminate": false,
    "sleep": 10
}
```

With this will perform a set of GET requests using Java Callable API in order to make sure that the system is being leveraged, preventing it from falling asleep.

**Note:** This will use Basic Auth to authenticate against the AEMaaCS instance and for that reason it is best to create a local user when using this tool.

**Note:** The sleep parameter is defined in seconds. This will hault the entire process for x seconds before trying again

**Note:** The interval parameter is defined in seconds and it will not call an AEM instance until an intervals worth of time has gone between the last invocation and the upcoming one

## How to use

`java -jar cloudmanager-ping.jar --location <file_path>`

file_path is the JSON file path location representing all of the AEMaaCS instances

**Note:** Internally we go straight to the Author and by pass the CDN and Dispatcher which prevents the request from being cached. If you are going through the CDN and/or Dispatcher make sure that the request does not get cached or else you will not be able to keep the environment awake

## How to build

`mvn clean install`

## Contributors

- [Patrique Legault](https://github.com/pat-lego)
