package com.haoisou.common.utils;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

public class RequestBody {
    private static final int BUFFER_SIZE = 8192;

    public RequestBody() {
    }

    public static String read(Reader reader) throws IOException {
        StringWriter writer = new StringWriter();

        String var2;
        try {
            write(reader, writer);
            var2 = writer.getBuffer().toString();
        } finally {
            writer.close();
        }

        return var2;
    }

    public static long write(Reader reader, Writer writer) throws IOException {
        return write(reader, writer, 8192);
    }

    public static long write(Reader reader, Writer writer, int bufferSize) throws IOException {
        long total = 0L;

        int read;
        for(char[] buf = new char[8192]; (read = reader.read(buf)) != -1; total += (long)read) {
            writer.write(buf, 0, read);
        }

        return total;
    }
}
