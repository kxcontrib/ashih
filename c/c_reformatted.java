package com.kx;

/**
   c_reformatted.java is c.java from 2007-02-12 converted
    to "verbose" style (as an aid to porting to C++).

   Official c.java changed as of 2007-04.  Details in k4 listbox
    message: 2007-04-20 21:16:53 / a@kx.com / java.sql.datetime

   Non-public features have been renamed to make it easier to
    follow the internal flow.

   Public features that have been renamed have a "backwards compat"
    forwarding method to ensure backwards compatibility.

   Reformatted by: Alvin Shih

   The changes from the original c.java have only been tested to
    compile.
   Backwards compatibility / correct behaviour not verified.
    Caveat emptor.
 */

//2006.10.06 truncate string at null
//2006.09.29 Date.Date(); sync with c.cs
//2006.04.22 NULL, e.g. Object[]x={NULL('i'),NULL('z')};
//jar cf c.jar *.class

import java.net.*;
import java.io.*;
import java.text.*;
import java.lang.reflect.Array;

public class c {
    public static void main(String[] args) {
	try { // s.setSoTimeout(ms);java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone("GMT"));
	    // c c=new c(new ServerSocket(5010));while(true)c.w(2,c.k());
	    // c c=new c("",5010);Object[]x={"GE",new Double(2.5),new
	    // Integer(23)};c.k(".u.upd","trade",x);
	    c c = new c("localhost", 5001);
	    while(true)c.w(2,c.k()) ;
	    // Object[]x={new Time(lt()),"xx",new Double(93.5),new
	    // Integer(300)};for(int
	    // i=0;i<1000;++i)c.ks("upsert","trade",x);c.k("");
	    // Flip t=td(c.k("select sum size by sym from trade"));
	    // O(n(t.x));O(n(t.y[0]));O(at(t.y[0],0)); //cols rows data
	    //	    c.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public Socket m_socket; // s

    DataInputStream m_dataInputStream; // i

    OutputStream m_outputStream; // o

    byte[] m_readBuffer, m_writeBuffer; // b, B

    int m_readBufferSize, m_writeBufferSize; // j, J

    boolean m_littleEndian; // a

    void io(Socket socket)
	throws IOException
    {
	m_socket = socket;
	m_dataInputStream = new DataInputStream(m_socket.getInputStream());
	m_outputStream = m_socket.getOutputStream();
    }

    public void close()
	throws IOException
    {
	m_socket.close();
	m_dataInputStream.close();
	m_outputStream.close();
    }

    public c(ServerSocket serverSocket)
	throws IOException
    {
	io(serverSocket.accept());
	m_dataInputStream.read(m_readBuffer = new byte[99]);
	m_outputStream.write(m_readBuffer, 0, 1);
    }

    public c(String hostName, int portNumber, String userName)
	throws KException, IOException
    {
	io(new Socket(hostName, portNumber));
	m_writeBuffer = new byte[1 + stringLength(userName)];
	m_writeBufferSize = 0;
	writeToBuffer(userName);
	m_outputStream.write(m_writeBuffer);
	if (1 != m_dataInputStream.read(m_writeBuffer, 0, 1))
	    throw new KException("access");
    }

    public c(String hostName, int portNumber)
	throws KException, IOException
    {
	this(hostName, portNumber, System.getProperty("user.name"));
    }

    public static class Date {
	public int i;

	public Date(int x) {
	    i = x;
	}

	public java.util.Date Date() {
	    return new java.util.Date(i == kNullInt ? kNullLong : 86400000L * (i + 10957));
	}

	public Date(long x) {
	    i = x == kNullLong ? kNullInt : (int) (x / 86400000) - 10957;
	}

	public Date(java.util.Date d) {
	    this(d.getTime());
	}

	public String toString() {
	    return i == kNullInt ? "" : f.format(this.Date());
	}
    }

    public static class Time {
	public int i;

	public Time(int x) {
	    i = x;
	}

	public Time(long x) {
	    i = (int) (x % 86400000);
	}

	public Time(java.util.Date d) {
	    this(d.getTime());
	}

	public String toString() {
	    return i == kNullInt ? "" : new Second(i / 1000).toString() + '.'
		+ new DecimalFormat("000").format(i % 1000);
	}
    }

    public static class Month {
	public int i;

	public Month(int x) {
	    i = x;
	}

	public String toString() {
	    int m = i + 24000, y = m / 12;
	    return i == kNullInt ? "" : toTwoDigitString(y / 100) + toTwoDigitString(y % 100) + "-"
		+ toTwoDigitString(1 + m % 12);
	}
    }

    public static class Minute {
	public int i;

	public Minute(int x) {
	    i = x;
	}

	public String toString() {
	    return i == kNullInt ? "" : toTwoDigitString(i / 60) + ":" + toTwoDigitString(i % 60);
	}
    }

    public static class Second {
	public int i;

	public Second(int x) {
	    i = x;
	}

	public String toString() {
	    return i == kNullInt ? "" : new Minute(i / 60).toString() + ':'
		+ toTwoDigitString(i % 60);
	}
    }

    public static class Dict {
	public Object x;

	public Object y;

	public Dict(Object X, Object Y) {
	    x = X;
	    y = Y;
	}
    }

    public static class Flip
    {
	public String[] x;

	public Object[] y;

	public Flip(Dict X) {
	    x = (String[]) X.x;
	    y = (Object[]) X.y;
	}

	public Object at(String s) {
	    return y[find(x, s)];
	}
    }

    public static Flip td(Object X)
    {
	if (kTypeOf(X) == 98)
	    return (Flip) X;
	Dict d = (Dict) X;
	Flip a = (Flip) d.x, b = (Flip) d.y;
	int m = numberOfValues(a.x), n = numberOfValues(b.x);
	String[] x = new String[m + n];
	System.arraycopy(a.x, 0, x, 0, m);
	System.arraycopy(b.x, 0, x, m, n);
	Object[] y = new Object[m + n];
	System.arraycopy(a.y, 0, y, 0, m);
	System.arraycopy(b.y, 0, y, m, n);
	return new Flip(new Dict(x, y));
    }

    // object.getClass().isArray() t(int[]) is .5 isarray is .1 lookup .05
    /// t(...)
    static int kTypeOf(Object x)
    {
	return x instanceof Boolean ? -1
	    : x instanceof Byte ? -4
	    : x instanceof Short ? -5
	    : x instanceof Integer ? -6
	    : x instanceof Long ? -7
	    : x instanceof Float ? -8
	    : x instanceof Double ? -9
	    : x instanceof Character ? -10
	    : x instanceof String ? -11
	    : x instanceof Month ? -13
	    : x instanceof java.util.Date ? -15
	    : x instanceof Time ? -19
	    : x instanceof Date ? -14
	    : x instanceof Minute ? -17
	    : x instanceof Second ? -18
	    : x instanceof boolean[] ? 1
	    : x instanceof byte[] ? 4
	    : x instanceof short[] ? 5
	    : x instanceof int[] ? 6
	    : x instanceof long[] ? 7
	    : x instanceof float[] ? 8
	    : x instanceof double[] ? 9
	    : x instanceof char[] ? 10
	    : x instanceof String[] ? 11
	    : x instanceof Month[] ? 13
	    : x instanceof java.util.Date[] ? 15
	    : x instanceof Time[] ? 19
	    : x instanceof Date[] ? 14
	    : x instanceof Minute[] ? 17
	    : x instanceof Second[] ? 18
	    : x instanceof Flip ? 98
	    : x instanceof Dict ? 99
	    : 0;
    }
    
    static int kNullInt = Integer.MIN_VALUE; // ni

    static long kNullLong = Long.MIN_VALUE; // nj

    static double kLongFloat = Double.NaN; // nf

    // nt
    static int[] typeToSize = { 0, 1, 0, 0, 1, 2, 4, 8, 4, 8, 1, 0, 0, 4, 4, 8, 0, 4, 4, 4 };

    // ns(...)
    static int stringLength(String s) {
	int i;
	return s == null ? 0
	    : -1 < (i = s.indexOf('\000')) ? i
	    : s.length();
    }

    public static Object[] NULL = { null, new Boolean(false), null, null,
				    new Byte((byte) 0), new Short(Short.MIN_VALUE), new Integer(kNullInt),
				    new Long(kNullLong), new Float(kLongFloat), new Double(kLongFloat), new Character(' '),
				    "", null, new Month(kNullInt), new Date(kNullInt), new java.util.Date(kNullLong),
				    null, new Minute(kNullInt), new Second(kNullInt), new Time(kNullInt) };

    public static Object NULL(char c) {
	return NULL[" b  xhijefcs mdz uvt".indexOf(c)];
    }

    // qn(...)
    static boolean isNullAtom(Object x) {
	int t = -kTypeOf(x);
	return t > 4 && x.equals(NULL[t]);
    }

    // backwards compat
    public static boolean qn(Object x) {
	return isNullAtom(x) ;
    }

    public static Object at(Object x, int i) {
	return isNullAtom(x = Array.get(x, i)) ? null : x;
    }

    public static void set(Object x, int i, Object y) {
	Array.set(x, i, null == y ? NULL[kTypeOf(x)] : y);
    }

    // n(...)
    static int numberOfValues(Object x) {
	return x instanceof Dict ? numberOfValues(((Dict) x).x)
	    : x instanceof Flip ? numberOfValues(((Flip) x).y[0])
	    : Array.getLength(x);
    }

    // nx(...)
    static int numberOfNetworkBytes(Object x) {
	int i = 0, n, t = kTypeOf(x), j;
	if (t == 99)
	    return 1 + numberOfNetworkBytes(((Dict) x).x) + numberOfNetworkBytes(((Dict) x).y);
	if (t == 98)
	    return 3 + numberOfNetworkBytes(((Flip) x).x) + numberOfNetworkBytes(((Flip) x).y);
	if (t < 0)
	    return t == -11 ? 2 + stringLength((String) x) : 1 + typeToSize[-t];
	j = 6; /// !!!
	n = numberOfValues(x);
	if (t == 0 || t == 11)
	    for (; i < n; ++i)
		j += t == 0 ? numberOfNetworkBytes(((Object[]) x)[i]) : 1 + stringLength(((String[]) x)[i]);
	else
	    j += n * typeToSize[t];
	return j;
    }

    // w(...)
    void writeToBuffer(byte x) {
	m_writeBuffer[m_writeBufferSize++] = x;
    }

    // w(...)
    void writeToBuffer(boolean x) {
	writeToBuffer((byte) (x ? 1 : 0));
    }

    // w(...)
    void writeToBuffer(short h) {
	writeToBuffer((byte) (h >> 8));
	writeToBuffer((byte) h);
    }

    // w(...)
    void writeToBuffer(int i) {
	writeToBuffer((short) (i >> 16));
	writeToBuffer((short) i);
    }

    // w(...)
    void writeToBuffer(long j) {
	writeToBuffer((int) (j >> 32));
	writeToBuffer((int) j);
    }

    // w(...)
    void writeToBuffer(float e) {
	writeToBuffer(Float.floatToIntBits(e));
    }

    // w(...)
    void writeToBuffer(double f) {
	writeToBuffer(Double.doubleToLongBits(f));
    }

    // w(...)
    void writeToBuffer(char c) {
	writeToBuffer((byte) c);
    }

    // w(...)
    void writeToBuffer(String s) {
	int i = 0, n = stringLength(s);
	for (; i < n;)
	    writeToBuffer(s.charAt(i++));
	m_writeBuffer[m_writeBufferSize++] = 0;
    }

    // w(...)
    void writeToBuffer(java.util.Date z) {
	long l = z.getTime();
	writeToBuffer(kNullLong == l ? kLongFloat : l / 8.64e7 - 10957);
    }

    // w(...)
    void writeToBuffer(Month m) {
	writeToBuffer(m.i);
    }

    // w(...)
    void writeToBuffer(Date d) {
	writeToBuffer(d.i);
    }

    // w(...)
    void writeToBuffer(Minute u) {
	writeToBuffer(u.i);
    }

    // w(...)
    void writeToBuffer(Second v) {
	writeToBuffer(v.i);
    }

    // w(...)
    void writeToBuffer(Time t) {
	writeToBuffer(t.i);
    }

    // w(...)
    void writeToBuffer(Object x) {
	int i = 0, n, t = kTypeOf(x);
	writeToBuffer((byte) t); // write the type
	if (t < 0) {
	    // write atom's value
	    switch (t) {
	    case -1:
		writeToBuffer(((Boolean) x).booleanValue());
		return;
	    case -4:
		writeToBuffer(((Byte) x).byteValue());
		return;
	    case -5:
		writeToBuffer(((Short) x).shortValue());
		return;
	    case -6:
		writeToBuffer(((Integer) x).intValue());
		return;
	    case -7:
		writeToBuffer(((Long) x).longValue());
		return;
	    case -8:
		writeToBuffer(((Float) x).floatValue());
		return;
	    case -9:
		writeToBuffer(((Double) x).doubleValue());
		return;
	    case -10:
		writeToBuffer(((Character) x).charValue());
		return;
	    case -11:
		writeToBuffer((String) x);
		return;
	    case -13:
		writeToBuffer((Month) x);
		return;
	    case -14:
		writeToBuffer((Date) x);
		return;
	    case -15:
		writeToBuffer((java.util.Date) x);
		return;
	    case -17:
		writeToBuffer((Minute) x);
		return;
	    case -18:
		writeToBuffer((Second) x);
		return;
	    case -19:
		writeToBuffer((Time) x);
		return;
	    }
	}
	if (t == 99) {
	    // write the dictionary's value
	    Dict r = (Dict) x;
	    writeToBuffer(r.x); // write the keys
	    writeToBuffer(r.y); // write the values
	    return;
	}
	m_writeBuffer[m_writeBufferSize++] = 0; /// !!!
	if (t == 98) {
	    // write the table's value
	    Flip r = (Flip) x;
	    m_writeBuffer[m_writeBufferSize++] = 99; /// !!!
	    writeToBuffer(r.x);
	    writeToBuffer(r.y);
	    return;
	}
	writeToBuffer(n = numberOfValues(x)); // write the length of the list
	for (; i < n; ++i)
	    // write the elements of the list
	    if (t == 0) writeToBuffer(((Object[]) x)[i]);
	    else if (t == 1) writeToBuffer(((boolean[]) x)[i]);
	    else if (t == 4) writeToBuffer(((byte[]) x)[i]);
	    else if (t == 5) writeToBuffer(((short[]) x)[i]);
	    else if (t == 6) writeToBuffer(((int[]) x)[i]);
	    else if (t == 7) writeToBuffer(((long[]) x)[i]);
	    else if (t == 8) writeToBuffer(((float[]) x)[i]);
	    else if (t == 9) writeToBuffer(((double[]) x)[i]);
	    else if (t == 10) writeToBuffer(((char[]) x)[i]);
	    else if (t == 11) writeToBuffer(((String[]) x)[i]);
	    else if (t == 13) writeToBuffer(((Month[]) x)[i]);
	    else if (t == 14) writeToBuffer(((Date[]) x)[i]);
	    else if (t == 15) writeToBuffer(((java.util.Date[]) x)[i]);
	    else if (t == 17) writeToBuffer(((Minute[]) x)[i]);
	    else if (t == 18) writeToBuffer(((Second[]) x)[i]);
	    else writeToBuffer(((Time[]) x)[i]);
    }

    // rb(...)
    boolean readBoolean()
    {
	return 1 == m_readBuffer[m_readBufferSize++];
    }

    // rh(...)
    short readShort()
    {
	int x = m_readBuffer[m_readBufferSize++], y = m_readBuffer[m_readBufferSize++];
	return (short) (m_littleEndian ? x & 0xff | y << 8 : x << 8 | y & 0xff);
    }

    // ri(...)
    int readInt()
    {
	int x = readShort(), y = readShort();
	return m_littleEndian ? x & 0xffff | y << 16 : x << 16 | y & 0xffff;
    }

    // rj(...)
    long readLong()
    {
	int x = readInt(), y = readInt();
	return m_littleEndian ? x & 0xffffffffL | (long) y << 32
	    : (long) x << 32 | y & 0xffffffffL;
    }

    // re(...)
    float readReal()
    {
	return Float.intBitsToFloat(readInt());
    }

    // rf(...)
    double readFloat()
    {
	return Double.longBitsToDouble(readLong());
    }

    // rc(...)
    char readChar()
    {
	return (char) (m_readBuffer[m_readBufferSize++] & 0xff);
    }

    // rs(...)
    String readString()
    {
	int i = m_readBufferSize;
	for (; m_readBuffer[m_readBufferSize++] != 0;)
	    ;
	return new String(m_readBuffer, i, m_readBufferSize - 1 - i);
    }

    // rm(...)
    Month readMonth()
    {
	return new Month(readInt());
    }

    // rd(...)
    Date readDate()
    {
	return new Date(readInt());
    }

    // rm(...)
    Minute readMinute()
    {
	return new Minute(readInt());
    }

    // rv(...)
    Second readSecond()
    {
	return new Second(readInt());
    }

    // rt(...)
    Time readTime()
    {
	return new Time(readInt());
    }

    // rz(...)
    java.util.Date readDateTime()
    {
	double f = readFloat();
	return new java.util.Date(Double.isNaN(f) ? kNullLong
				  : (long) (.5 + 8.64e7 * (f + 10957)));
    }

    // r(...)
    Object readObject()
    {
	int i = 0, n, t = m_readBuffer[m_readBufferSize++];
	if (t < 0)
	    switch (t) {
	    case -1: return new Boolean(readBoolean());
	    case -4: return new Byte(m_readBuffer[m_readBufferSize++]);
	    case -5: return new Short(readShort());
	    case -6: return new Integer(readInt());
	    case -7: return new Long(readLong());
	    case -8: return new Float(readReal());
	    case -9: return new Double(readFloat());
	    case -10: return new Character(readChar());
	    case -11: return readString();
	    case -13: return readMonth();
	    case -14: return readDate();
	    case -15: return readDateTime();
	    case -17: return readMinute();
	    case -18: return readSecond();
	    case -19: return readTime();
	    }
	if (t > 99) {
	    if (t == 100) {
		readString();
		return readObject();
	    }
	    if (t < 104)
		return m_readBuffer[m_readBufferSize++] == 0 && t == 101 ? null : "func";
	    if (t > 105)
		readObject();
	    else
		for (n = readInt(); i < n; i++)
		    readObject();
	    return "func";
	}
	if (t == 99)
	    return new Dict(readObject(), readObject());
	m_readBufferSize++;
	if (t == 98)
	    return new Flip((Dict) readObject());
	// read in lists
	n = readInt(); // number of elements
	switch (t) {
	case 0:
	    Object[] L = new Object[n];
	    for (; i < n; i++)
		L[i] = readObject();
	    return t == 0 ? L : (Object) "func";
	case 1:
	    boolean[] B = new boolean[n];
	    for (; i < n; i++)
		B[i] = readBoolean();
	    return B;
	case 4:
	    byte[] G = new byte[n];
	    for (; i < n; i++)
		G[i] = m_readBuffer[m_readBufferSize++];
	    return G;
	case 5:
	    short[] H = new short[n];
	    for (; i < n; i++)
		H[i] = readShort();
	    return H;
	case 6:
	    int[] I = new int[n];
	    for (; i < n; i++)
		I[i] = readInt();
	    return I;
	case 7:
	    long[] J = new long[n];
	    for (; i < n; i++)
		J[i] = readLong();
	    return J;
	case 8:
	    float[] E = new float[n];
	    for (; i < n; i++)
		E[i] = readReal();
	    return E;
	case 9:
	    double[] F = new double[n];
	    for (; i < n; i++)
		F[i] = readFloat();
	    return F;
	case 10:
	    char[] C = new char[n];
	    for (; i < n; i++)
		C[i] = readChar();
	    return C;
	case 11:
	    String[] S = new String[n];
	    for (; i < n; i++)
		S[i] = readString();
	    return S;
	case 13:
	    Month[] M = new Month[n];
	    for (; i < n; i++)
		M[i] = readMonth();
	    return M;
	case 14:
	    Date[] D = new Date[n];
	    for (; i < n; i++)
		D[i] = readDate();
	    return D;
	case 17:
	    Minute[] U = new Minute[n];
	    for (; i < n; i++)
		U[i] = readMinute();
	    return U;
	case 15:
	    java.util.Date[] Z = new java.util.Date[n];
	    for (; i < n; i++)
		Z[i] = readDateTime();
	    return Z;
	case 18:
	    Second[] V = new Second[n];
	    for (; i < n; i++)
		V[i] = readSecond();
	    return V;
	case 19:
	    Time[] T = new Time[n];
	    for (; i < n; i++)
		T[i] = readTime();
	    return T;
	}
	return null;
    }


    void writeCommand(int commandType, Object x)
	throws IOException
    {
	int n = numberOfNetworkBytes(x) + 8;
	synchronized (m_outputStream) {
	    m_writeBuffer = new byte[n];
	    m_writeBuffer[0] = 0;
	    m_writeBuffer[1] = (byte) commandType;
	    m_writeBufferSize = 4; /// skipped 2 bytes(?)
	    writeToBuffer(n);
	    writeToBuffer(x);
	    m_outputStream.write(m_writeBuffer);
	}
    }

    // backwards compat
    void w(int commandType, Object x)
	throws IOException
    {
	writeCommand(commandType, x) ;
    }

    static final int COMMAND_TYPE_ASYNC = 0 ;

    void invokeAsync(String s)
	throws IOException
    {
	writeCommand(COMMAND_TYPE_ASYNC, charArrayFromString(s));
    }

    // backwards compat
    public void ks(String s)
	throws IOException
    {
	invokeAsync(s) ;
    }

    char[] charArrayFromString(String s)
    {
	return s.toCharArray();
    }

    void invokeAsync(String s, Object x)
	throws IOException
    {
	Object[] a = { charArrayFromString(s), x };
	writeCommand(0, a);
    }

    // backwards compat
    public void ks(String s, Object x)
	throws IOException
    {
	invokeAsync(s, x) ;
    }

    void invokeAsync(String s, Object x, Object y)
	throws IOException
    {
	Object[] a = { charArrayFromString(s), x, y };
	writeCommand(0, a);
    }

    // backwards compat
    public void ks(String s, Object x, Object y)
	throws IOException
    {
	invokeAsync(s, x, y) ;
    }


    void invokeAsync(String s, Object x, Object y, Object z)
	throws IOException
    {
	Object[] a = { charArrayFromString(s), x, y, z };
	writeCommand(0, a);
    }

    // backwards compat
    public void ks(String s, Object x, Object y, Object z)
	throws IOException
    {
	invokeAsync(s, x, y, z) ;
    }

    public static class KException
	extends Exception
    {
	KException(String s)
	{
	    super(s);
	}
    }

    Object readResult()
	throws KException, IOException
    {
	synchronized (m_dataInputStream) {
	    m_dataInputStream.readFully(m_readBuffer = new byte[8]);
	    m_littleEndian = m_readBuffer[0] == 1;
	    m_readBufferSize = 4;
	    m_dataInputStream.readFully(m_readBuffer = new byte[readInt() - 8]);
	    if (m_readBuffer[0] == -128) {
		m_readBufferSize = 1;
		throw new KException(readString());
	    }
	    m_readBufferSize = 0;
	    return readObject();
	}
    }

    // backwards compat
    public Object k() 
	throws KException, IOException
    {
	return readResult() ;
    }

    static final int COMMAND_TYPE_SYNC = 1 ;

    synchronized Object invokeSync(Object x)
	throws KException, IOException
    {
	writeCommand(COMMAND_TYPE_SYNC, x);
	return readResult();
    }

    // backwards compat
    public Object k(Object x)
	throws KException, IOException
    {
	return invokeSync(x) ;
    }

    Object invokeSync(String s)
	throws KException, IOException
    {
	return invokeSync(charArrayFromString(s));
    }

    // backwards compat
    public Object k(String s)
	throws KException, IOException
    {
	return invokeSync(s) ;
    }

    
    Object invokeSync(String s, Object x)
	throws KException, IOException
    {
	Object[] a = { charArrayFromString(s), x };
	return invokeSync(a);
    }

    // backwards compat
    public Object k(String s, Object x)
	throws KException, IOException
    {
	return invokeSync(s, x) ;
    }

    Object invokeSync(String s, Object x, Object y)
	throws KException, IOException
    {
	Object[] a = { charArrayFromString(s), x, y };
	return invokeSync(a);
    }

    // backwards compat
    public Object k(String s, Object x, Object y)
	throws KException, IOException
    {
	return invokeSync(s, x, y) ;
    }

    Object invokeSync(String s, Object x, Object y, Object z)
	throws KException, IOException
    {
	Object[] a = { charArrayFromString(s), x, y, z };
	return invokeSync(a);
    }

    // backwards compat
    public Object k(String s, Object x, Object y, Object z)
	throws KException, IOException
    {
	return invokeSync(s, x, y, z) ;
    }


    static final int COMMAND_TYPE_RESULT = 2 ;


    static int find(String[] x, String y) {
	int i = 0;
	for (; i < x.length && !x[i].equals(y);)
	    ++i;
	return i;
    }

    // O(...)
    static void print(Object x) {
	System.out.println(x);
    }

    // O(...)
    static void print(int x) {
	System.out.println(x);
    }

    // O(...)
    static void print(boolean x) {
	System.out.println(x);
    }

    // O(...)
    static void print(long x) {
	System.out.println(x);
    }

    // O(...)
    static void print(double x) {
	System.out.println(x);
    }

    // y(...)
    static long timeMillis() {
	return System.currentTimeMillis();
    }

    static long s_lastTimeMillis; // t

    // tm(...)
    static void timeSinceMillis() {
	long u = s_lastTimeMillis;
	s_lastTimeMillis = timeMillis();
	if (u > 0)
	    print(s_lastTimeMillis - u);
    }

    static long lt() {
	long t = timeMillis();
	return t + java.util.TimeZone.getDefault().getOffset(t);
    }

    // i2(...)
    static String toTwoDigitString(int i) {
	return new DecimalFormat("00").format(i);
    }

    static SimpleDateFormat f = new SimpleDateFormat("yyyy.MM.dd");
    static {
	f.setTimeZone(java.util.TimeZone.getTimeZone("GMT"));
    }
}
