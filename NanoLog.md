##NanoLog - minimalistic lean logger

Compared to widely used logback or log4j, this logger has following distinctions:

- has minimum required functionity;
- single threaded (multithreading can be achieved in future versions by appropriate sink);
- should be very fast;
- very small footprint (log4j or logback takes 500-800kb);
- separates severity and logging level notions;
- support additional **severity** level ALWAYS;
- does not support "weird" **logging** level FATAL.

With above features, this logger is perfect for command line utilities.

### Logging levels, logging level ALWAYS

Every logging system supports following notions:
- **severity level** - the property of every logged record - characterizes it's type and importance ;
- **logging level** - the property of logging system - defines the amount of data which are saved, and the amount of ignored data.

In all known to me loggers both above notions are messed and mixed.
In most cases its OK.

For example, DEBUG severity level of messages says,
that this record is important only during the development/debugging,
and accordingly any logging level higher than DEBUG cuts off such records.

For example, ERROR severity level of messages says, that this is very
important message, and accordingly it can be silenced only by setting
the logging level to FATAL, which is unlikely to be done.

But in one special case there is no correlation. That's for the logging of important
events of an application life cycle. What happens here:
- a developer creates log record about an application start fact;
naturally this record is of INFO **severity level**;
- after some production period, when an application is very stable,
but requires more speed, support team sets the **logging level** to WARN,
thus killing regular info messages, but at the same time removing
**very important** messages about start and stop of the application;
- in case of any problem, an investigator is unable even to find out,
when an application started or stopped;
- and the only solution for all known loggers - to assing to start/stop records
severity level of WARN or even ERROR - which is logically not correct.

NanoLog supports one special severity level - ALWAYS.
Records of ALWAYS severity does not signify fatal errors or warnings,
they are just of informative type, but they are logged **always** independently
of current logging level.

List of supported **severity** levels:
- SeverityLevel.ALWAYS(0)
- SeverityLevel.FATAL(1)
- SeverityLevel.ERROR(2)
- SeverityLevel.WARN(3)
- SeverityLevel.INFO(4)
- SeverityLevel.DEBUG(5)
- SeverityLevel.TRACE(6)

List of supported **logging** levels:
- LogLevel.ERROR(2)
- LogLevel.WARN(3)
- LogLevel.INFO(4)
- LogLevel.DEBUG(5)
- LogLevel.TRACE(6)

