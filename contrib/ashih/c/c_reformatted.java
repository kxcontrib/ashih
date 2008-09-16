package kx; //jar cf c.jar kx/*.class

/**
c_reformatted.java is c.java from 2008-09-16 reformatted
 for readability (as an aid to porting to C++) and to
 make it easier to see where support may be somewhat limited.

Features have been renamed to make it easier to follow the internal flow.

Public features that have been renamed have a backwards compatibility
 forwarding method to ensure backwards compatibility.

Reformatted by: Alvin Shih

The changes from the original c.java have only been tested to compile.
Backwards compatibility / correct behaviour not verified.
 Caveat emptor!
*/

import java.net.*;
import java.io.*;
import java.sql.*;
import java.lang.reflect.Array;
import java.text.*;

public class c{
    public static void main(String[]args){
        try{ //s.setSoTimeout(ms);
            //c c=new c(new ServerSocket(5010));while(true)c.w(2,c.k());
            //c c=new c("",5010);Object[]x={"GE",new Double(2.5),new Integer(23)};c.k(".u.upd","trade",x);
            c c=new c("",5001);  //Object[]x={"asd",new char[0]};c.k("insert","t",x);
            //c.tz=java.util.TimeZone.getTimeZone("GMT");
            c.k("0N!",O(new Time(t())));
            //Object[]x={new Time(t()),"xx",new Double(93.5),new Integer(300)};for(int i=0;i<1000;++i)c.ks("upsert","trade",x);c.k("");
            //Flip t=td(c.k("select sum size by sym from trade"));O(n(t.x));O(n(t.y[0]));O(at(t.y[0],0)); //cols rows data
            c.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public java.util.TimeZone tz=java.util.TimeZone.getDefault();
    long o(long x){return tz.getOffset(x);}
    long lg(long x){return x+o(x);}
    long gl(long x){return x-o(x-o(x));}
    static long k=86400000L*10957;
    void w(java.util.Date z){long l=z.getTime();w(l==nj?nf:(lg(l)-k)/8.64e7);}
    Timestamp rz(){double f=rf();return new Timestamp(Double.isNaN(f)?nj:gl(k+(long)(8.64e7*f)));}
    void w(Time t){long l=t.getTime();w(l==nj?ni:(int)(lg(l)%86400000));}
    Time rt(){int i=ri();return new Time(i==ni?nj:gl(i));}
    void w(Date d){long l=d.getTime();w(l==nj?ni:(int)((lg(l)-k)/86400000));}
    Date rd(){int i=ri();return new Date(i==ni?nj:gl(k+86400000L*i));}

    public Socket m_socket;
    DataInputStream m_inStream;
    OutputStream m_outStream;
    byte[]m_readBuf,m_writeBuf;
    int m_readBufSize,m_writeBufSize;
    boolean m_littleEndian;
    
    void io(Socket x)throws IOException{
        m_socket=x;
        m_inStream=new DataInputStream(m_socket.getInputStream());
        m_outStream=m_socket.getOutputStream();
    }

    public void close()throws IOException{m_socket.close();m_inStream.close();m_outStream.close();}

    public c(ServerSocket s)throws IOException{
        io(s.accept());
        m_inStream.read(m_readBuf=new byte[99]);
        m_outStream.write(m_readBuf,0,1);
    }
    public c(String h,int p,String u)throws KException,IOException{
        io(new Socket(h,p));
        m_writeBuf=new byte[1+ns(u)];
        m_writeBufSize=0;
        w(u);
        m_outStream.write(m_writeBuf);
        if(1!=m_inStream.read(m_writeBuf,0,1))throw new KException("access");
    }
    public c(String h,int p)throws KException,IOException{
        this(h,p,System.getProperty("user.name"));
    }

    public static class Month{
        public int i;
        public Month(int x){i=x;}
        public String toString(){int m=i+24000,y=m/12;return i==ni?"":i2(y/100)+i2(y%100)+"-"+i2(1+m%12);}
    }

    public static class Minute{
        public int i;
        public Minute(int x){i=x;}
        public String toString(){return i==ni?"":i2(i/60)+":"+i2(i%60);}
    }

    public static class Second{
        public int i;
        public Second(int x){i=x;}
        public String toString(){return i==ni?"":new Minute(i/60).toString()+':'+i2(i%60);}
    }

    public static class Dict{
        public Object x;
        public Object y;
        public Dict(Object X,Object Y){x=X;y=Y;}
    }
    static int find(String[]x,String y){int i=0;for(;i<x.length&&!x[i].equals(y);)++i;return i;}

    public static class Flip{
        public String[]x;
        public Object[]y;
        public Flip(Dict X){x=(String[])X.x;y=(Object[])X.y;}
        public Object at(String s){return y[find(x,s)];}
    }

    public static Flip td(Object X){
        if(typeNum(X)==98)return(Flip)X;
        Dict d=(Dict)X;
        Flip a=(Flip)d.x,b=(Flip)d.y;
        int m=numberOfValues(a.x),n=numberOfValues(b.x);
        String[]x=new String[m+n];
        System.arraycopy(a.x,0,x,0,m);
        System.arraycopy(b.x,0,x,m,n);
        Object[]y=new Object[m+n];
        System.arraycopy(a.y,0,y,0,m);
        System.arraycopy(b.y,0,y,m,n);
        return new Flip(new Dict(x,y));
    }
    //object.getClass().isArray()   t(int[]) is .5 isarray is .1 lookup .05

    public static int t(Object x){return typeNum(x);}
    private static int typeNum(Object x){
        return x instanceof Boolean?-1
            :x instanceof Byte?-4
            :x instanceof Short?-5
            :x instanceof Integer?-6
            :x instanceof Long?-7
            :x instanceof Float?-8
            :x instanceof Double?-9
            :x instanceof Character?-10
            :x instanceof String?-11
            :x instanceof Month?-13
            :x instanceof Time?-19
            :x instanceof Date?-14
            :x instanceof java.util.Date?-15
            :x instanceof Minute?-17
            :x instanceof Second?-18
            :x instanceof boolean[]?1
            :x instanceof byte[]?4
            :x instanceof short[]?5
            :x instanceof int[]?6
            :x instanceof long[]?7
            :x instanceof float[]?8
            :x instanceof double[]?9
            :x instanceof char[]?10
            :x instanceof String[]?11
            :x instanceof Month[]?13
            :x instanceof Time[]?19
            :x instanceof Date[]?14
            :x instanceof java.util.Date[]?15
            :x instanceof Minute[]?17
            :x instanceof Second[]?18
            :x instanceof Flip?98
            :x instanceof Dict?99
            :0;
    }
    static int ni=Integer.MIN_VALUE;
    static long nj=Long.MIN_VALUE;
    static double nf=Double.NaN;
    static int[]nt={0,1,0,0,1,2,4,8,4,8,1,0,0,4,4,8,0,4,4,4};
    static int ns(String s){int i;return s==null?0:-1<(i=s.indexOf('\000'))?i:s.length();}

    public static Object[]NULL={null,new Boolean(false),null,null,new Byte((byte)0),new Short(Short.MIN_VALUE),new Integer(ni),new Long(nj),new Float(nf),new Double(nf),
                new Character(' '),"",null,new Month(ni),new Date(nj),new Timestamp(nj),null,new Minute(ni),new Second(ni),new Time(nj)};

    public static Object NULL(char c){return NULL[" b  xhijefcs mdz uvt".indexOf(c)];}

    
    public static boolean qn(Object x){return isNullAtom(x);}
    private static boolean isNullAtom(Object x){int t=-typeNum(x);return t>4&&x.equals(NULL[t]);}
    public static Object at(Object x,int i){return isNullAtom(x=Array.get(x,i))?null:x;}
    public static void set(Object x,int i,Object y){Array.set(x,i,null==y?NULL[typeNum(x)]:y);}
    public static int n(Object x){return numberOfValues(x);}
    
    private static int numberOfValues(Object x){
        return x instanceof Dict?numberOfValues(((Dict)x).x)
                :x instanceof Flip?numberOfValues(((Flip)x).y[0])
                :Array.getLength(x);
    }

    public static int nx(Object x){return numberOfNetworkBytes(x);}
    private static int numberOfNetworkBytes(Object x){
        int i=0,n,t=typeNum(x),j;
        if(t==99)return 1+numberOfNetworkBytes(((Dict)x).x)+numberOfNetworkBytes(((Dict)x).y);
        if(t==98)return 3+numberOfNetworkBytes(((Flip)x).x)+numberOfNetworkBytes(((Flip)x).y);
        if(t<0)return t==-11?2+ns((String)x):1+nt[-t];j=6;
        n=numberOfValues(x);
        if(t==0||t==11)
            for(;i<n;++i)j+=t==0?numberOfNetworkBytes(((Object[])x)[i]):1+ns(((String[])x)[i]);
            else j+=n*nt[t];
        return j;
    }
    void w(byte x){m_writeBuf[m_writeBufSize++]=x;}
    void w(boolean x){w((byte)(x?1:0));}
    void w(short h){w((byte)(h>>8));w((byte)h);}
    void w(int i){w((short)(i>>16));w((short)i);}
    void w(long j){w((int)(j>>32));w((int)j);}
    void w(float e){w(Float.floatToIntBits(e));}
    void w(double f){w(Double.doubleToLongBits(f));}
    void w(char c){w((byte)c);}
    void w(String s){int i=0,n=ns(s);for(;i<n;)w(s.charAt(i++));m_writeBuf[m_writeBufSize++]=0;}
    void w(Month m){w(m.i);}
    void w(Minute u){w(u.i);}
    void w(Second v){w(v.i);}
    void w(Object x){
        int i=0,n,t=typeNum(x);
        w((byte)t);
        if(t<0)
        switch(t){
        case-1:w(((Boolean)x).booleanValue());return;
        case-4:w(((Byte)x).byteValue());return;
        case-5:w(((Short)x).shortValue());return;
        case-6:w(((Integer)x).intValue());return;
        case-7:w(((Long)x).longValue());return;
        case-8:w(((Float)x).floatValue());return;
        case-9:w(((Double)x).doubleValue());return;
        case-10:w(((Character)x).charValue());return;
        case-11:w((String)x);return;
        case-13:w((Month)x);return;
        case-14:w((Date)x);return;
        case-15:w((java.util.Date)x);return;
        case-17:w((Minute)x);return;
        case-18:w((Second)x);return;
        case-19:w((Time)x);return;
        }
        if(t==99){Dict r=(Dict)x;w(r.x);w(r.y);return;}
        m_writeBuf[m_writeBufSize++]=0; // padding?
        if(t==98){
            Flip r=(Flip)x;
            m_writeBuf[m_writeBufSize++]=99; /// !!!
            w(r.x);
            w(r.y);
            return;
        }
        w(n=numberOfValues(x));
        for(;i<n;++i)
            if(t==0)w(((Object[])x)[i]);
            else if(t==1)w(((boolean[])x)[i]);
            else if(t==4)w(((byte[])x)[i]);
            else if(t==5)w(((short[])x)[i]);
            else if(t==6)w(((int[])x)[i]);
            else if(t==7)w(((long[])x)[i]);
            else if(t==8)w(((float[])x)[i]);
            else if(t==9)w(((double[])x)[i]);
            else if(t==10)w(((char[])x)[i]);
            else if(t==11)w(((String[])x)[i]);
            else if(t==13)w(((Month[])x)[i]);
            else if(t==14)w(((Date[])x)[i]);
            else if(t==15)w(((java.util.Date[])x)[i]);
            else if(t==17)w(((Minute[])x)[i]);
            else if(t==18)w(((Second[])x)[i]);
            else w(((Time[])x)[i]);
    }

    boolean rb(){return 1==m_readBuf[m_readBufSize++];}
    short rh(){int x=m_readBuf[m_readBufSize++],y=m_readBuf[m_readBufSize++];return(short)(m_littleEndian?x&0xff|y<<8:x<<8|y&0xff);}
    int ri(){int x=rh(),y=rh();return m_littleEndian?x&0xffff|y<<16:x<<16|y&0xffff;}
    long rj(){int x=ri(),y=ri();return m_littleEndian?x&0xffffffffL|(long)y<<32:(long)x<<32|y&0xffffffffL;}
    float re(){return Float.intBitsToFloat(ri());}
    double rf(){return Double.longBitsToDouble(rj());}
    char rc(){return(char)(m_readBuf[m_readBufSize++]&0xff);}
    String rs()throws UnsupportedEncodingException{
        int i=m_readBufSize;
        for(;m_readBuf[m_readBufSize++]!=0;);
        return new String(m_readBuf,i,m_readBufSize-1-i,"ISO-8859-1");
    }
    Month rm(){return new Month(ri());}
    Minute ru(){return new Minute(ri());}
    Second rv(){return new Second(ri());}

    Object r()throws UnsupportedEncodingException{
        int i=0,n,t=m_readBuf[m_readBufSize++];
        if(t<0)
            switch(t){
            case-1:return new Boolean(rb());
            case-4:return new Byte(m_readBuf[m_readBufSize++]);
            case-5:return new Short(rh());
            case-6:return new Integer(ri());
            case-7:return new Long(rj());
            case-8:return new Float(re());
            case-9:return new Double(rf());
            case-10:return new Character(rc());
            case-11:return rs();
            case-13:return rm();
            case-14:return rd();
            case-15:return rz();
            case-17:return ru();
            case-18:return rv();
            case-19:return rt();
            }
        if(t>99){
            if(t==100){rs();return r();}
            if(t<104)return m_readBuf[m_readBufSize++]==0&&t==101?null:"func";
            if(t>105)r();
            else for(n=ri();i<n;i++)r();
            return"func";
        }
        if(t==99)return new Dict(r(),r());
        m_readBufSize++; // padding?
        if(t==98)return new Flip((Dict)r());
        n=ri();
        switch(t){
        case 0:Object[]L=new Object[n];for(;i<n;i++)L[i]=r();return t==0?L:(Object)"func";  
        case 1:boolean[]B=new boolean[n];for(;i<n;i++)B[i]=rb();return B;
        case 4:byte[]G=new byte[n];for(;i<n;i++)G[i]=m_readBuf[m_readBufSize++];return G;   
        case 5:short[]H=new short[n];for(;i<n;i++)H[i]=rh();return H;
        case 6:int[]I=new int[n];for(;i<n;i++)I[i]=ri();return I;       
        case 7:long[]J=new long[n];for(;i<n;i++)J[i]=rj();return J;
        case 8:float[]E=new float[n];for(;i<n;i++)E[i]=re();return E;     
        case 9:double[]F=new double[n];for(;i<n;i++)F[i]=rf();return F;
        case 10:char[]C=new char[n];for(;i<n;i++)C[i]=rc();return C;    
        case 11:String[]S=new String[n];for(;i<n;i++)S[i]=rs();return S;
        case 13:Month[]M=new Month[n];for(;i<n;i++)M[i]=rm();return M;  
        case 14:Date[]D=new Date[n];for(;i<n;i++)D[i]=rd();return D;
        case 17:Minute[]U=new Minute[n];for(;i<n;i++)U[i]=ru();return U;
        case 15:Timestamp[]Z=new Timestamp[n];for(;i<n;i++)Z[i]=rz();return Z;
        case 18:Second[]V=new Second[n];for(;i<n;i++)V[i]=rv();return V;
        case 19:Time[]T=new Time[n];for(;i<n;i++)T[i]=rt();return T;
        }
        return null;
    }

    static final int COMMAND_TYPE_ASYNC = 0;
    static final int COMMAND_TYPE_SYNC = 1;
    static final int COMMAND_TYPE_RESULT = 2;
    
    void w(int commandType,Object x)throws IOException{
        int n=numberOfNetworkBytes(x)+8;
        synchronized(m_outStream){
            m_writeBuf=new byte[n];
            m_writeBuf[0]=0;
            m_writeBuf[1]=(byte)commandType;
            m_writeBufSize=4;
            w(n);
            w(x);
            m_outStream.write(m_writeBuf);
        }
    }
    public void ks(Object x)throws IOException{w(0,x);}
    public void ks(String s)throws IOException{w(0,cs(s));} char[]cs(String s){return s.toCharArray();}
    public void ks(String s,Object x)throws IOException{Object[]a={cs(s),x};w(0,a);}
    public void ks(String s,Object x,Object y)throws IOException{Object[]a={cs(s),x,y};w(0,a);}
    public void ks(String s,Object x,Object y,Object z)throws IOException{Object[]a={cs(s),x,y,z};w(0,a);}

    public static class KException extends Exception{KException(String s){super(s);}}

    public Object k()throws KException,IOException,UnsupportedEncodingException{
        synchronized(m_inStream){
            m_inStream.readFully(m_readBuf=new byte[8]);
            m_littleEndian=m_readBuf[0]==1;m_readBufSize=4;
            m_inStream.readFully(m_readBuf=new byte[ri()-8]);
            if(m_readBuf[0]==-128){m_readBufSize=1;throw new KException(rs());}
            m_readBufSize=0;
            return r();
        }
    }

    public synchronized Object k(Object x)throws KException,IOException{w(COMMAND_TYPE_SYNC,x);return k();}

    public Object k(String s)throws KException,IOException{return k(cs(s));}
    public Object k(String s,Object x)throws KException,IOException{Object[]a={cs(s),x};return k(a);}
    public Object k(String s,Object x,Object y)throws KException,IOException{Object[]a={cs(s),x,y};return k(a);}
    public Object k(String s,Object x,Object y,Object z)throws KException,IOException{Object[]a={cs(s),x,y,z};return k(a);}

    public static Object O(Object x){System.out.println(x);return x;}
    public static void O(int x){System.out.println(x);}
    public static void O(boolean x){System.out.println(x);}
    public static void O(long x){System.out.println(x);}
    public static void O(double x){System.out.println(x);}
    public static long t(){return System.currentTimeMillis();}
    static long t;
    public static void tm(){long u=t;t=t();if(u>0)O(t-u);}
    static String i2(int i){return new DecimalFormat("00").format(i);}
}
//2008.08.14 String(,,,"ISO-8859-1") to avoid mutex
//2007.10.18 tz 
//2007.08.06 kx
//2007.04.20 sql.{Date|Time|Timestamp}
