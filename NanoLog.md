##NanoLog - minimalistic lean logger

Compared to widely used logback or log4j, this logger has following distinctions:

- has minimum required functionity;
- single threaded (multithreading can be achieved in future versions by appropriate sink);
- should be very fast;
- very small footprint (log4j or logback takes 500-800kb);
- additional logging level ALWAYS.

With above features, this logger is perfect for command line utilities.
