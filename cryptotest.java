import java.io.*;
import java.math.*;
import java.util.*;
import javax.crypto.*;
import java.security.*;
import java.lang.*;

class cryptotest
{
static String filename = " ";
static String line = " ";
static String plainText1 = " ";

    public static void main(String[] args){
if (0 < args.length) {
    filename = args[0];
    }
File f = new File(filename);

try{
InputStream fIn = new FileInputStream(f);
BufferedReader br = new BufferedReader(new InputStreamReader(fIn));
StringBuffer sb = new StringBuffer();
while((line = br.readLine())!=null){
	sb.append(line);
	sb.append("\n");
}	
plainText1=sb.toString();
}
catch (IOException e){
}

AES aes = new AES(plainText1, 128);
AES aer = new AES(plainText1, 256);

RSA rs = new RSA(plainText1, f, 1024);
RSA rsa = new RSA(plainText1, f, 4096);

MD5 md1 =new MD5(plainText1,"HmacMD5");
MD5 md =new MD5(plainText1,"HmacSHA1");
MD5 md2 =new MD5(plainText1,"HmacSHA256");

signature sig=new signature(plainText1);

}
}

class AES
{
double ar1[]=new double[100];   
double ar2[]=new double[100];   // Store the values of encrypt and decrypt time
AES(String plainText, int mode){
int i=0;

while(i<100)
{
try{

//Key Generation
SecureRandom sr = SecureRandom.getInstance("NativePRNGNonBlocking");
byte[] d= new byte[mode/8];
 sr.nextBytes(d);
KeyGenerator key= KeyGenerator.getInstance("AES");
key.init(mode, sr);
SecretKey secret= key.generateKey();

//Encryption
double startTime = System.nanoTime();
Cipher cipherText= Cipher.getInstance("AES/CTR/NoPadding");
cipherText.init(Cipher.ENCRYPT_MODE,secret);
byte[] byteplainText=plainText.getBytes();
byte[] bytecipherText=cipherText.doFinal(byteplainText);
double stopTime = System.nanoTime();

double elapsedTimeEncrypt = stopTime - startTime;
System.out.println("["+(i+1)+"]AES"+mode+" Encrypt Running Time = "+elapsedTimeEncrypt/1000000000);

ar1[i]= elapsedTimeEncrypt;               // // Store the values of encrypt and decrypt time 


//Decryption
double startTime1 = System.nanoTime();
String strdecryptText=" ";
AlgorithmParameters algPar= cipherText.getParameters();
byte[] para= algPar.getEncoded();

AlgorithmParameters alg= AlgorithmParameters.getInstance("AES");
alg.init(para);

Cipher decryptText= Cipher.getInstance("AES/CTR/NoPadding");
decryptText.init(Cipher.DECRYPT_MODE,secret, alg);
byte[] bytedecryptText = decryptText.doFinal(bytecipherText);
strdecryptText = new String(bytedecryptText);
//System.out.println(strdecryptText);
double stopTime1 = System.nanoTime();
 double elapsedTimeDecrypt = stopTime1 - startTime1;
 
System.out.println("["+(i+1)+"]AES"+mode+" Decrypt Running Time = "+elapsedTimeDecrypt/1000000000+"\n");
ar2[i]= elapsedTimeDecrypt;                  // Store the values of encrypt and decrypt time 


}

catch (NoSuchAlgorithmException noSuchAlgo) {
			System.out.println(" No Such Algorithm exists " + noSuchAlgo);
		}

		catch (NoSuchPaddingException noSuchPad) {
			System.out.println(" No Such Padding exists " + noSuchPad);
		}

		catch (InvalidKeyException invalidKey) {
			System.out.println(" Invalid Key " + invalidKey);
		}

		catch (BadPaddingException badPadding) {
			System.out.println(" Bad Padding " + badPadding);
		}

		catch (IllegalBlockSizeException illegalBlockSize) {
			System.out.println(" Illegal Block Size " + illegalBlockSize);
		}

		catch (InvalidAlgorithmParameterException invalidParam) {
			System.out.println(" Invalid Parameter " + invalidParam);
		}
		catch(IOException nosuchfile)
		{
			
		}
i=i+1;
//System.out.println(i);
}
mean x1 =new mean(ar1,"Encrytion");		
mean x2 =new mean(ar2,"Decrytion");	
median z1 = new median (ar1,"Encrytion");
median z2 = new median (ar2,"Decrytion");
}
}



class MD5{
MD5(String input, String algo){
int y=0;
double arMD[]=new double[100]; 
while (y<100){
try {
//Key gerneration
SecureRandom sr1 = SecureRandom.getInstance("NativePRNGNonBlocking");
byte[] d1= new byte[256];
 sr1.nextBytes(d1);
KeyGenerator key= KeyGenerator.getInstance(algo);
key.init(256, sr1);
SecretKey secret= key.generateKey();

//Encryption
double startTime2 = System.nanoTime();
 Mac mac = Mac.getInstance(algo);
    mac.init(secret);
byte[] bytes = mac.doFinal(input.getBytes());
double stopTime2 = System.nanoTime();
      double elapsedTimeDecrypt = stopTime2 - startTime2;
 System.out.println("["+(y+1)+"]"+algo+" Elapsed Time = "+elapsedTimeDecrypt/1000000000);
arMD[y]= elapsedTimeDecrypt; 
//System.out.println(arMD[y]);
y=y+1;

} 

catch (NoSuchAlgorithmException noSuchAlgo) {
			System.out.println(" No Such Algorithm exists " + noSuchAlgo);
		}

		catch (InvalidKeyException invalidKey) {
			System.out.println(" Invalid Key " + invalidKey);
		}
	}	
mean x =new mean(arMD,"Encrytion Time");		
median z = new median (arMD, "Encrytion Time");

}		
} 


 class RSA{  
   
   RSA(String plainText, File d,int RSAmode){
   try{
Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
int z=0;
double sum1=0;
double sum2=0;
double time[]=new double[2]; 
double time1[]=new double[2];
int by;
if (RSAmode ==1024){
	by=117;
}
else {by=501;}
while(z< 2)
{
	
//Key generation	
SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
byte[] d1= new byte[RSAmode/8];
sr.nextBytes(d1);
  
KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
keyGen.initialize(RSAmode, sr);
KeyPair key = keyGen.generateKeyPair();
FileInputStream p = new FileInputStream(d);
BufferedInputStream cs= new BufferedInputStream(p);
int bytesRead = 0;
sum1=0;
sum2=0;
byte[] contents =  new byte[by];
while ((bytesRead = cs.read(contents)) != -1)
{
	
//Encryption	
double startTime = System.nanoTime();
cipher.init(Cipher.ENCRYPT_MODE, key.getPublic());
byte []cipherText = cipher.doFinal(contents);
double stopTime= System.nanoTime();
double elapsedTimeEncrypt = stopTime - startTime;


//Decryption
double startTime1 = System.nanoTime();
cipher.init(Cipher.DECRYPT_MODE, key.getPrivate());
cipher.doFinal(cipherText);
 double stopTime1= System.nanoTime();

double elapsedTimeDecrypt = stopTime1 - startTime1;
 sum1=sum1+elapsedTimeEncrypt;
  sum2=sum2+elapsedTimeDecrypt;
 }
 cs.close();
System.out.println("["+(z+1)+"] RSA"+RSAmode+" Encrypt Running Time = "+sum1/1000000000);
  System.out.println("["+(z+1)+"] RSA"+RSAmode+" Decrypt Running Time = "+sum2/1000000000+"\n");
time[z]=sum1;
time1[z]=sum2;
z=z+1;
   }
  
mean x1 =new mean(time, "Encrytion");	
mean x2 =new mean(time1, "Decrytion");   
median x3 = new median (time, "Encrytion");
median x4 = new median (time1, "Decrytion");
   }
catch (NoSuchAlgorithmException noSuchAlgo) {
			System.out.println(" No Such Algorithm exists " + noSuchAlgo);
		}

		catch (NoSuchPaddingException noSuchPad) {
			System.out.println(" No Such Padding exists " + noSuchPad);
		}

		catch (InvalidKeyException invalidKey) {
			System.out.println(" Invalid Key " + invalidKey);
		}

		catch (BadPaddingException badPadding) {
			System.out.println(" Bad Padding " + badPadding);
		}

		catch (IllegalBlockSizeException illegalBlockSize) {
			System.out.println(" Illegal Block Size " + illegalBlockSize);
		}
		catch(IOException nosuchfile)
		{
			
		}
		 
   
  
}
} 


class signature{
	
signature(String content)
{    

	
   try {

 int s=0;
	double sigarr1[]=new double[100];
	double sigarr2[]=new double[100];
     while(s<100)
	{  
//Key Generation
    KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
    SecureRandom random = SecureRandom.getInstance("NativePRNGNonBlocking");
    keyGen.initialize(4096,random);
    KeyPair pair = keyGen.generateKeyPair();
	PrivateKey priv = pair.getPrivate();
	PublicKey pub = pair.getPublic();
	 
// Signing
	 double sigstartTime1 = System.nanoTime();
    Signature sig = Signature.getInstance("SHA256WithRSA");
    sig.initSign(priv);
    sig.update(content.getBytes());
	byte[] signatureBytes = sig.sign();
double sigstopTime1 = System.nanoTime();

double EncryptTimesig = sigstopTime1 - sigstartTime1;
// Verification   
Signature sig1 = Signature.getInstance("SHA256WithRSA");
	double sigstartTime2 = System.nanoTime();
	sig1.initVerify(pub);
	sig1.update(content.getBytes());
 boolean result = sig1.verify(signatureBytes); 

 
double sigstopTime2 = System.nanoTime();
double DecryptTimesig = sigstopTime2 - sigstartTime2;

 System.out.println("["+(s+1)+"]"+"Signature Encrypt Running Time ="+EncryptTimesig/1000000000);
   System.out.println("["+(s+1)+"]"+"Signature Decrypt Running Time ="+DecryptTimesig/1000000000);
    if (result== true){
	System.out.println("Verification Result: "+result);
	}
	 
   sigarr1[s]=EncryptTimesig;
	sigarr2[s]= DecryptTimesig;
	s=s+1;
   }
mean x5 =new mean(sigarr1,"Digital Signature Encryption");		
median z5 = new median (sigarr1,"Digital Signature Encryption");

mean x6 =new mean(sigarr2,"Verification");		
median z6 = new median (sigarr2,"Verification");
   }
   catch(Exception e) {
		}
 
   } 
	}	
	



class mean{
public  mean(double [] me, String print) {
    double sum = 0;
    for (int alpha = 0; alpha < me.length; alpha++) {
        sum = sum + me[alpha];
    }
    System.out.println( "Mean "+print+"= "+(sum / me.length/1000000000));
}
}


class median{
public  median(double [] array, String pri)
	{
	
	Arrays.sort(array);
int middle = (array.length)/2;
    if ((array.length)%2 == 1) {
        System.out.println( array[middle]);
    } else {
        System.out.println("Median "+pri+"= "+((array[middle-1] + array[middle]) / 2)/1000000000);
    }
	
    }
	
}
