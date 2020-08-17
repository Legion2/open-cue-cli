# Open CUE CLI [![Build](https://github.com/Legion2/open-cue-cli/workflows/Build/badge.svg)](https://github.com/Legion2/open-cue-cli/actions?query=workflow%3ABuild)
Command Line Interface (CLI) to change iCUE profiles from the command line and play multiple profiles at once.
This CLI uses the iCUE Game SDK via the [Open CUE Service](https://github.com/Legion2/open-cue-service).
You can use all your customized iCUE profiles.

## Getting Started
Download and start the [Open CUE Service](https://github.com/Legion2/open-cue-service).
Download the [latest release](https://github.com/Legion2/open-cue-cli/releases/latest) of Open CUE CLI and extract the archive.
Call the cli tool `open-cue-cli.bat` or `open-cue-cli` from a command prompt with the option `--help`.

```
Usage: open-cue-cli [OPTIONS] COMMAND [ARGS]...

Options:
  -p, --port <port>        Port of the Open CUE Service
  -H, --host <host or ip>  Hostname or ip address of the Open CUE Service
  --version                Show the version and exit
  -h, --help               Show this message and exit

Commands:
  sdk
  profile
```

### Examples
Activates the profile `Fire`
```
> open-cue-cli profile activate Fire
```

Lists all profiles
```
> open-cue-cli profile list
```

Plays the profile `Wave` as event for a short time
```
> open-cue-cli profile trigger Wave
```

### Profiles
All profiles that you want to use with the Open CUE CLI must be in the iCUE `GameSdkEffects` directory.
On Windows this is in the installation directory of iCUE `C:\Program Files (x86)\Corsair\CORSAIR iCUE Software\GameSdkEffects`.
These profiles are just the exported files when you export a profile in iCUE.
When export profiles from iCUE only select "Lighting Effects" in the export settings.

Profiles are grouped into games, this is because the SDK was designed for iCUE Game integration.
So each subdirectory of `GameSdkEffects` represent a game and contains all it's profiles and a `priorities.cfg` file.

### Use own profiles/effects
The subdirectories of `GameSdkEffects` correspond to games, but you can also create own subdirectories.
If you want to add your own profiles/effects, create a new directory in the `GameSdkEffects` directory called `profiles`.
Create a new text file named `priorities.cfg` in the `profiles` directory.

> In Windows Administrator permissions are required to create new files in the directory, so you may create the file in another directory and then move it in the `profiles` directory.

The file must contain all profile names one per line with a unique priority value.
```properties
MyProfile=3
Other_Profile=8
Test=245
```
Profile names **MUST** only contain normal characters "a-z", "A-Z" and "_".
Also don't use language specific characters like ä and é.
You must set the name of the profile, when you export it from iCUE, you can't change it later, because it's stored inside the profile file.
The priorities comes into play when you activate two profiles, then the profile with the higher priority is shown on top of the other.

## Packaging of this application
This package is provide as zip containing optimized runtime images.
Just download the [zip archive for your platform](https://github.com/Legion2/open-cue-cli/releases), extract it where you want and then execute one of the executable scripts from the extracted archive.

### Why not provide native executable
Native executables require curl or other native implementations to do http calls.
The linking in kotlin is very complex and dynamic linking is uncommon on Windows.
I didn't find a way to statically link curl into to the windows native executable produced by kotlin.
