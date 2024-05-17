# javananolib
Several tiny reusable pieces of code, could be adopted on per source file basis

All the classes reside in ```net.nenko.lib``` directory, and can be taken to another
project by copying appropriate source file. Good practice to place the source file
to the same package.

No jar library planned to create with this classes. But for every class good unit tests
are planned.

So every reusable class should be represented by minimum 3 files -
sourse, test source, and documentation md file.

Classes are compilable with Java 8 and do not require extra 3rd-party dependencies.

## Content

- ```net.nenko.lib.NanoLog``` - minimalistic lean logger
- ```net.nenko.lib.NanoArgsParser``` - simple command line arguments parser


