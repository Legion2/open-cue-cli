# Open CUE CLI [![Build](https://github.com/Legion2/open-cue-cli/workflows/Build/badge.svg)](https://github.com/Legion2/open-cue-cli/actions?query=workflow%3ABuild)
Command Line Interface (CLI) to change iCUE profiles form the command line and play multiple profiles at once.
This CLI uses the iCUE Game SDK via the [iCUE Unofficial Game Integration](https://github.com/Zac-McDonald/iCUE-Custom-Game-Integration) server.

## Getting Started
Download and start the [iCUE Unofficial Game Integration](https://github.com/Zac-McDonald/iCUE-Custom-Game-Integration) server.
Download the [latest release](https://github.com/Legion2/open-cue-cli/releases/latest) of Open CUE CLI and extract the archive.
You must at least have Java 8 installed.
Call the cli tool `open-cue-cli.bat` or `open-cue-cli` from a command prompt with the option `--help`.

```
Usage: open-cue-cli [OPTIONS] COMMAND [ARGS]...

Options:
  -p, --port <port>        Port of the iCUE SDK HTTP Server
  -H, --host <host or ip>  Hostname or ip address of the iCUE SDK HTTP Server
  --version                Show the version and exit
  -h, --help               Show this message and exit

Commands:
  game
  state
  event
  profile
```

### Examples
Activates the profile `SDKL_Fire` from the game `Common` and also set the game active
```
open-cue-cli state set -s -g Common SDKL_Fire
```

Lists all profiles from the current game
```
open-cue-cli profile list
```

Plays the profile `SDKL_Explosion` of the current game as event
```
open-cue-cli event set SDKL_Explosion
```

### Profiles
All profiles that you want to use with the Open CUE CLI must be in the iCUE `GameSdkEffects` directory.
On Windows this is in the installation directory of iCUE `C:\Program Files (x86)\Corsair\CORSAIR iCUE Software\GameSdkEffects`.
These profiles are like 

### Use own profiles/effects
Directories in `GameSdkEffects` correspond to games, but the games must not exist.
If you want to add your own profiles/effects, create a new directory in the `GameSdkEffects` directory called `profiles`.
Create a new text file named `priorities.cfg` in the `profiles` directory.

> In Windows Administrator permissions are required to create new files in the directory, so you may create the file in another directory and then move it.

The file must contain all profiles one per line with a unique priority value.
```properties
MyProfile1=3
OtherProfile=8
Test=245
```
The priority comes into play when you activate two profiles, then the profile with the higher priority is shown on top of the other.

## Packaging of this application
This package is provide as zip containing executables and dependencies as jars.
A Java Runtime Environment (JRE) must be installed on to run the application.
Just download the zip archive, extract it where you want and then execute one of the executable scripts from the extracted archive.

### Why not provide native executable
Native executables require curl or other native implementations to do http calls.
The linking in kotlin is very complex and dynamic linking is uncommon on Windows.
I didn't find a way to statically link curl into to the windows native executable produced by kotlin.
