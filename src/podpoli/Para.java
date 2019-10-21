/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podpoli;

/**
 *
 * @author alask
 */
public class Para {
    public int key;
    public char value;
    Para(int Key, char Value){
        this.key = Key;
        this.value = Value;
    }
   public char getValue(){
       return this.value;
   }
   
   public int getKey(){
       return this.key;
   }
}
