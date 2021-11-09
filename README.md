# AEM Cloud Manager Poke

A utility that reads in a JSON file in the following format:

```
[
    {
        "url": "https://....",
        "interval": 1234,
        "lastInvocation": null
    },
    {
        ...
    }

]
```

With this will perform a set of GET requests using Java Callable API in order to make sure that the system is being leveraged, preventing it from falling asleep.

## How to use

`java -jar cloudmanager-ping.jar --location <file_path>`

file_path is the JSON file path location representing all of the AEMaaCS instances

## How to build

`mvn clean install`

## Contributors

- [Patrique Legault](https://github.com/pat-lego)