/**
 * Title		:	Cyclic Redundancy Check Implementation in JAVA
 * Autor		:	Shadab Shaikh
 * Date			:	29-12-2018
 * Version		:	2.0
 * Availability	:	https://github.com/shadabsk
 **/

import java.util.*;				//using scanner to accept input from user
class Crc{
	static void divide(int datawordapp[],int count,int datawordlength,int codewordlength,int appendbit){		//function to divide according to crc algo on sender side
		int temp[]=new int[10];					//for storing the left out elements
		int divisor[]={1,0,1,1};				//constant diviser being used
		int i;
		if(datawordapp[0]!=0){					//if dataword first element is non-zero value performing x-or with divisor
			for(i=0;i<datawordlength;i++){
				if(datawordapp[i]==divisor[i]){		//if both value are same then result 0
					temp[i]=0;
				}
				else{
					temp[i]=1;						//else result is 1
				}
				temp[i+1]=datawordapp[i+1+count];	//moving the element and taking the next element i.e 5th element on second iteration
			}
			
		}
		else{									//if dataword first element is 0 then divisor is all 0
			for(i=0;i<datawordlength;i++){
				int divisor2[]={0,0,0,0};
				if(datawordapp[i]==divisor2[i]){	//performing x-or with similar logic just divisor difference
					temp[i]=0;
				}
				else{
					temp[i]=1;
				}
				temp[i+1]=datawordapp[i+1+count];
			}
			
		}
		
		for(i=1;i<=datawordlength;i++){		//storing the resultant temp array to datawordappended array
			datawordapp[i-1]=temp[i];		//leaving one-one element behind and iterating 
		}
		
		count+=1;							//incrementing count flag
		if(count<appendbit)
			divide(datawordapp,count,datawordlength,codewordlength,appendbit);	//recursively peroforming same function
		else{
			for(i=0;i<4;i++){				//for debugging
				//System.out.print(datawordapp[i]);
			}
		}
	}
	
	static void divider(int gencodeword[],int count,int datawordlength,int codewordlength,int appendbit){	//function used in receiver side with similar logic to decode the generated code word
		int temp1[]=new int[10];
		int divisor[]={1,0,1,1};
		int i;
		if(gencodeword[0]!=0){
			for(i=0;i<datawordlength;i++){
				if(gencodeword[i]==divisor[i]){
					temp1[i]=0;
				}
				else{
					temp1[i]=1;
				}
				temp1[i+1]=gencodeword[i+1+count];
			}
		}
		else{
			for(i=0;i<datawordlength;i++){
				int divisor2[]={0,0,0,0};
				if(gencodeword[i]==divisor2[i]){
					temp1[i]=0;
				}
				else{
					temp1[i]=1;
				}
				temp1[i+1]=gencodeword[i+1+count];
			}
		}
		
		for(i=1;i<=datawordlength;i++){
			gencodeword[i-1]=temp1[i];
		}
		
		count+=1;
		if(count<appendbit)
			divider(gencodeword,count,datawordlength,codewordlength,appendbit);
		else{
			if(gencodeword[0]!=0){
				for(i=0;i<4;i++){
					if(gencodeword[i]==divisor[i]){
						temp1[i]=0;
					}
					else{
						temp1[i]=1;
					}
				}
			}
			else{
				for(i=0;i<4;i++){
					int divisor2[]={0,0,0,0};
					if(gencodeword[i]==divisor2[i]){
						temp1[i]=0;
					}
					else{
						temp1[i]=1;
					}
				}
			
			}
			for(i=0;i<4;i++){
				gencodeword[i]=temp1[i];
			}
		}
	}
	
	public static void main(String args[]){
		int dataword[]=new int[4];
		int datawordr[]=new int[4];
		int gencodeword[]=new int[7];
		int i,endresult=0,check=0,check2=0;
		int codewordlength;
		int datawordlength;
		Scanner s=new Scanner(System.in);
		System.out.print("\n\nPlease Enter the length of data word (k)\n\n");
		datawordlength=s.nextInt();
		System.out.print("\n\nPlease Enter the length of code word (n)\n\n");
		codewordlength=s.nextInt();
		//*******************Sender side   **********/
		System.out.print("\n\nPlease Enter the number in binary input for the sending side\n\n");
		for(i=0;i<datawordlength;i++){
			check=s.nextInt();
			if(check==0||check==1)
				dataword[i]=check;
			else{
					System.out.print("\n\ninvalid input\n\n");		//for validation
					return;
				}
		}
		int appendbit=codewordlength-datawordlength;			//using crc formula to determine the bits to be appended
			
		int datawordapp[]=new int[codewordlength];	
		for(i=0;i<datawordlength;i++){
			datawordapp[i]=dataword[i];					//creating generated codeword array and appended bit array 
			gencodeword[i]=dataword[i];							
		}
			
		for(i=datawordlength;i<=appendbit;i++){
			datawordapp[i]=0;						//the vacant position is assigned 0
		}
			
		divide(datawordapp,0,datawordlength,codewordlength,appendbit);	//calling sender function to generate code word
		for(i=1;i<datawordlength;i++){
			gencodeword[i+datawordlength-1]=datawordapp[i];			//storing the value in generated code word array
		}
		
		System.out.print("\n\ndata word is: ");
		for(i=0;i<datawordlength;i++){
			System.out.print(dataword[i]);
		}
		
		System.out.print("\tThe number of 0's appended with n-k is: "+appendbit);
			
		System.out.print("\tgenerated code word is\t");
		for(i=0;i<gencodeword.length;i++){
			System.out.print(gencodeword[i]);
		}
		//*******************Receiver side   **********/	
		System.out.print("\n\n\nPlease Enter the number in binary input for the receiving side\n\n");
		for(i=0;i<datawordlength;i++){
			check2=s.nextInt();
			if(check2==0||check2==1)
				datawordr[i]=check2;
			else{
				System.out.print("\n\ninvalid input\n\n");
				return;
			}
			gencodeword[i]=datawordr[i];
		}
		divider(gencodeword,0,datawordlength,codewordlength,appendbit);		//calling decoding function at receiver side
			
		System.out.print("\n\nthe remainder of the code word with divisor 1011 in the receiving side is : ");
			
		for(i=1;i<datawordlength;i++){
			System.out.print(gencodeword[i]);
		}
		for(i=0;i<datawordlength;i++){
			endresult+=gencodeword[i];
		}
			
			
		if(endresult!=0)		//if returned generated code word are non zero the packet is discarded
			System.out.println("\n\nThe resultant  syndrome is non-zero hence packet is discareded");
		else                   //Else packet is accepted
			System.out.println("\n\nThe resultant syndrome is zero hence the packet is accepted");
	}
	
}
