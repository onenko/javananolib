package net.nenko.lib;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class NanoLog {
	private static final DateTimeFormatter FORMATTER_SIMPLE = DateTimeFormatter.ofPattern("yyMMdd HHmmss");
	private static final DateTimeFormatter FORMATTER_COMPLEX = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
	private static final Object[] DUMMY_ARG_ARRAY = new Object[0];
	private PrintStream printStream;
	private LogLevel level;
	private LogStyle style;

	public NanoLog(LogLevel level) {
		this(level, LogStyle.CMD_LINE, null);
	}

	public NanoLog(LogLevel level, LogStyle style, String fileTemplate) {
		this.level = level;
		this.style = style;
		if(fileTemplate == null) {
			printStream = System.out;
		} else {
			File file = new File(fileTemplate);
			try {
				printStream = new PrintStream(file);
			} catch(FileNotFoundException e) {
				printStream = System.out;
				printStream.println("NanoLog error - failed to open/create file " + file);
			}
		}
	}

	public void error(String format) {
		if(LogLevel.ERROR.isEnabled(level)) {
			String record = style.buildRecord(format, LogLevel.ERROR, DUMMY_ARG_ARRAY);
			writeRecord(record);
		}
	}

	public void error(String format, Object... arguments) {
		if(arguments.length > 0) {
			Object lastArg = arguments[arguments.length - 1];
			if(lastArg instanceof Throwable) {
				String throwableInfo = getThrowableInfo((Throwable) lastArg);
				arguments[arguments.length - 1] = throwableInfo;
				// Custom code to print exception
				Object[] newArguments = new Object[arguments.length - 1];
				// Copy elements except the last one
				System.arraycopy(arguments, 0, newArguments, 0, newArguments.length);
				String record = style.buildRecord(format, LogLevel.ERROR, newArguments) + throwableInfo;
				writeRecord(record);
				return;
			}
		}
		String record = style.buildRecord(format, LogLevel.ERROR, arguments);
		writeRecord(record);
	}

	public void warn(String format) {
		if(LogLevel.WARN.isEnabled(level)) {
			String record = style.buildRecord(format, LogLevel.WARN, DUMMY_ARG_ARRAY);
			writeRecord(record);
		}
	}

	public void warn(String format, Object... arguments) {
		String record = style.buildRecord(format, LogLevel.WARN, arguments);
		writeRecord(record);
	}

	public void info(String format) {
		if(LogLevel.INFO.isEnabled(level)) {
			String record = style.buildRecord(format, LogLevel.INFO, DUMMY_ARG_ARRAY);
			writeRecord(record);
		}
	}

	public void info(String format, Object... arguments) {
		if(LogLevel.INFO.isEnabled(level)) {
			String record = style.buildRecord(format, LogLevel.INFO, arguments);
			writeRecord(record);
		}
	}

	public void always(String format) {
		String record = style.buildRecord(format, LogLevel.INFO, DUMMY_ARG_ARRAY);
		writeRecord(record);
	}

	public void always(String format, Object... arguments) {
		String record = style.buildRecord(format, LogLevel.INFO, arguments);
		writeRecord(record);
	}

	public void debug(String format) {
		if(LogLevel.DEBUG.isEnabled(level)) {
			String record = style.buildRecord(format, LogLevel.DEBUG, DUMMY_ARG_ARRAY);
			writeRecord(record);
		}
	}

	public void debug(String format, Object... arguments) {
		if(LogLevel.DEBUG.isEnabled(level)) {
			String record = style.buildRecord(format, LogLevel.DEBUG, arguments);
			writeRecord(record);
		}
	}

	public void trace(String format) {
		if(LogLevel.TRACE.isEnabled(level)) {
			String record = style.buildRecord(format, LogLevel.TRACE, DUMMY_ARG_ARRAY);
			writeRecord(record);
		}
	}

	public void trace(String format, Object... arguments) {
		if(LogLevel.TRACE.isEnabled(level)) {
			String record = style.buildRecord(format, LogLevel.TRACE, arguments);
			writeRecord(record);
		}
	}

	private static void appendMsg(StringBuilder sb, String template, Object[] arguments) {
		String[] parts = template.split("\\{\\}", 9999);
		for(int i = 0; i < parts.length; i++) {
			sb.append(parts[i]);
			if(i < parts.length - 1) {
				sb.append( i < arguments.length ? arguments[i].toString() : "{}");
			}
		}
	}

	private void writeRecord(String record) {
		printStream.print(record);
	}

	private String getThrowableInfo(Throwable throwable) {
		if (throwable != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			throwable.printStackTrace(pw);
			return "Exception: " + throwable.getClass().getName() + ", Message: " + throwable.getMessage() + "\nStack Trace:\n" + sw.toString();
		} else {
			return "No Exception Message";
		}
	}

	// TODO: design something like style of log record formatting, to avoid slow templating
	public enum LogStyle {
		CMD_LINE {
			@Override
			public String buildRecord(String template, LogLevel severity, Object[] args) {
				StringBuilder sb = new StringBuilder(template.length() + args.length * 10);
				if(args.length == 0) {
					sb.append(template);
				} else {
					appendMsg(sb, template, args);
				}
				sb.append('\n');
				return sb.toString();
			}
		},
		SIMPLE {
			@Override
			public String buildRecord(String template, LogLevel severity, Object[] args) {
				StringBuilder sb = new StringBuilder(template.length() + args.length * 10);
				LocalDateTime now = LocalDateTime.now();
				String formattedDateTime;
				formattedDateTime = now.atZone(ZoneId.of("UTC")).format(FORMATTER_SIMPLE);
				sb.append(formattedDateTime);
				sb.append(' ').append(severity.name().substring(0,1)).append(" - ");
				if(args.length == 0) {
					sb.append(template);
				} else {
					appendMsg(sb, template, args);
				}
				sb.append('\n');
				return sb.toString();
			}
		},
		COMPLEX {
			@Override
			public String buildRecord(String template, LogLevel severity, Object[] args) {
				StringBuilder sb = new StringBuilder(template.length() + args.length * 10);
				LocalDateTime now = LocalDateTime.now();
				String formattedDateTime;
				formattedDateTime = now.atZone(ZoneId.of("UTC")).format(FORMATTER_COMPLEX);
				sb.append(formattedDateTime);
				sb.append(' ').append(severity.name()).append(" - ");
				if(args.length == 0) {
					sb.append(template);
				} else {
					appendMsg(sb, template, args);
				}
				sb.append('\n');
				return sb.toString();
			}
		};
		public abstract String buildRecord(String template, LogLevel severity, Object[] args);
	};

	public static enum LogLevel {
		ALWAYS(0),	// printed despite current logging level
		FATAL(1),
		ERROR(2),
		WARN(3),
		INFO(4),
		DEBUG(5),
		TRACE(6);
		private int lvl;
	
		private LogLevel(int lvl) {
			this.lvl = lvl;
		}
		
		public boolean isEnabled(LogLevel thresholdLevel) {
			return this.lvl <= thresholdLevel.lvl;
		}
	};


	private static File fileFromTemplate(String fileTemplate) {
		// TODO: elaborate nice date/time substitution
		return new File(fileTemplate);
	}
}
