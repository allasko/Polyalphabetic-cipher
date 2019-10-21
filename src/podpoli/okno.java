
package podpoli;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.Map;
import java.util.TreeMap;
import java.util.Arrays; 
import java.util.LinkedHashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class okno extends javax.swing.JFrame {
 String klucz, jawny, sciezkaJ, szyfrogram, sciezkaS, deszyfrowany, zaszyfrowany, odszyfrowany;
    
    public okno() {
        initComponents();
    }
    
public ArrayList<Para> tworzeniePar (String klucz){
        klucz = klucz.replaceAll(" ", "");
        
        char [] passTable = klucz.toCharArray();
        char [] passNS = klucz.toCharArray();
        
        ArrayList<Para> pary = new ArrayList<>();
        
        // sortowanie tablicy z kluczem by ponumerowac litery w kolejnosci alfabetycznej
        Arrays.sort(passTable);
        
        //przypisanie numerów do danej litery
      for(int i=0; i<passNS.length;i++){
           for(int j =0; j<passTable.length;j++ ){
                if(passNS[i]==passTable[j]){
                    
                Para temp = new Para((j+1),passNS[i]);
                pary.add(temp); 
                passTable[j] = 'ń';
                break;
                }
            }
        }
    
    return pary;
}    

 //ustalanie kolumn
       

public ArrayList<Integer> przekatnaP(String klucz){
       int keySize = klucz.length();
       ArrayList<Integer> prawo = new ArrayList<>();
       int mod;
       for (int i =2; i<=keySize; i++){
           mod = i % 5;
           if(mod == 2){
               prawo.add(i);
           }
       }
    return prawo;
}
  
public ArrayList<Integer> przekatnaL(String klucz){
       int keySize = klucz.length();
       ArrayList<Integer> lewo = new ArrayList<>();
       int mod;
       for (int i =2; i<=keySize; i++){
           mod = i % 5;
           if(mod == 4){
               lewo.add(i);
           }
       }
    return lewo;
}  
    
public ArrayList<Character> szyfrowanie(){
    ArrayList<Character> szyfr = new ArrayList<>();
    ArrayList<Character> szyfr2 = new ArrayList<>();
    ArrayList<Character> prawaPrzek = new ArrayList<>();
    ArrayList<Character> lewaPrzek = new ArrayList<>();
    ArrayList<Character> Pion = new ArrayList<>();
      
    ArrayList<Integer> prawo = new ArrayList<>(); 
    ArrayList<Integer> lewo = new ArrayList<>();
    ArrayList<Para> pary = new ArrayList<>();
    
    double dlKlucza, dlJawnego;
    int x, y, k;
    char M[][];
    
    char[] tekst = jawny.toCharArray();
    pary = tworzeniePar(klucz);
    prawo = przekatnaP(klucz);     
    lewo = przekatnaL(klucz);        
    dlKlucza = klucz.length();
    dlJawnego = jawny.length();
    
    x = (int)Math.ceil(dlJawnego/dlKlucza);
    y = klucz.length();
    k = 0;
    
    M = new char[x][y];
       //zapisanie do macierzy
      for(int i = 0; i<x; i++){
          for(int j = 0; j<y; j++){
              
              if(k<tekst.length){
                 M[i][j] = tekst[k];
              k++;   
              }else{
                  M[i][j] = '-';
              }
          }
      }
       
      //odczytywanie po prawej przekatnej
      
      for(int i = 0; i < prawo.size(); i++){
          for(int j = 0; j< pary.size(); j++){
              if(prawo.get(i) == pary.get(j).getKey()){
               int l = 0;
                  int p = j;
                  while(l < x){
                    prawaPrzek.add(M[l][p]);
                    M[l][p]= '_';
                  l++;
                  p++;
                  if(l==x || p==y){
                      break;
                }
                  
                  
              }
          }
      }
      }

       //odczytywanie po lewej przekatnej
       
      for(int i = 0; i < lewo.size(); i++){
          for(int j = 0; j< pary.size(); j++){
              if(lewo.get(i) == pary.get(j).getKey()){
               int l = 0;
                  int p = j;
                  while(l < x){
                    lewaPrzek.add(M[l][p]);
                    M[l][p]= '_';
                  l++;
                  p--;
                  if(l==x ||p<0){
                     break;
                }
                  
                  
              }
          }
      }}
      //usuwanie znakow podkreslenia dla dobrego obliczenia pozycji w koncowej tablicy
      for(int i = 0; i< lewaPrzek.size(); i++){
           if(lewaPrzek.get(i).charValue() =='_'){
               lewaPrzek.remove(i);
       }
      }
       
       //czytanie pionowo
       
      for(int i = 0; i < y; i++){
          for(int j = 0; j < x; j++){
                    
                    Pion.add(M[j][i]);
                    M[j][i]= '_';
                  
                } 
              }
          
      
      
       // zlozenie list 
       
       szyfr.addAll(prawaPrzek);
       szyfr.addAll(lewaPrzek);
       szyfr.addAll(Pion);
       for(int i = 0; i < szyfr.size(); i++){
           if(szyfr.get(i).charValue() !='_'){
               szyfr2.add(szyfr.get(i));
            }
       }
      
    
   return szyfr2; 
}

public ArrayList<Character> deszyfrowanie(){
    
    ArrayList<Integer> prawo = new ArrayList<>(); 
    ArrayList<Integer> lewo = new ArrayList<>();
    ArrayList<Para> pary = new ArrayList<>();
    ArrayList<Character> szyfr = new ArrayList<>();
    ArrayList<Character> odszyfrowane = new ArrayList<>();
    int x, y, k;
    double dlKlucza, dlJawnego;
    
    dlKlucza = klucz.length();
    dlJawnego = jawny.length();
    
    x = (int)Math.ceil(dlJawnego/dlKlucza);
    y = klucz.length();
    k = 0;
    
    pary = tworzeniePar(klucz);
    prawo = przekatnaP(klucz);     
    lewo = przekatnaL(klucz); 
    szyfr = szyfrowanie();
    
    //tworzenie macierzy do odszyfrowania
      char [][] O = new char[x][y];
      int s = 0;
      
      //wypełnienie macierzy do odszyfrowania znakami spoza alfabetu
       for(int i = 0; i<x; i++){
          for(int j = 0; j<y; j++){
              O[i][j] = '?';
              
            }
        } 
       
       
            //odszyfrowanie po prawej przekatnej
      for(int m = 0; m < prawo.size(); m++){
                for(int n = 0; n< pary.size(); n++){
                    if(prawo.get(m) == pary.get(n).getKey()){
                    int l = 0;
                    int p = n;
                    while(l < x){
                      if(s<prawo.size()*x){
                        O[l][p] = szyfr.get(s);  
                        
                      }else{
                          O[l][p] = '*';
                      }
                        s++;
                     l++;
                     p++;
                    if(l==x ||p==y){
                      break;
                      
                        }
                    }
                  
                  
                    }
                }
              }
           
      
      //odszyfrowanie po lewej przekatnej
       
      for(int i = 0; i < lewo.size(); i++){
          for(int j = 0; j< pary.size(); j++){
              if(lewo.get(i) == pary.get(j).getKey()){
               int l = 0;
                  int p = j;
                  while(l < x){
                    if(s<lewo.size()*x+prawo.size()*x && O[l][p] == '?'){
                       O[l][p] = szyfr.get(s);
                       s++;
                  l++;
                  p--;
                          
                    }else{
                        l++;
                        p--;
                    }
                       
                  if(l==x ||p<0){
                      break;
                   
                }
                  
              }
          }
      } 
      }
      //odszyfrowanie pionowo
      
       for(int i = 0; i < y; i++){
          for(int j = 0; j < x; j++){
                    
                   if(s<szyfr.size()&& O[j][i] == '?'){
                       O[j][i] = szyfr.get(s);
                       s++;
                        }
                   
                } 
              }
      
        for(int i =0; i<x ; i++){
           for (int j=0; j<y; j++){
           odszyfrowane.add(O[i][j]);
       }
      }
          
        return odszyfrowane;
} 

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tKlucz = new javax.swing.JTextField();
        bPrzeslij1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tSciezkaJ = new javax.swing.JTextField();
        tJawny = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        bPrzeslij2 = new javax.swing.JButton();
        bPrzeslij3 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        tSzyfrogram = new javax.swing.JTextField();
        bZapisz = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        bPrzeslij5 = new javax.swing.JButton();
        bPrzeslij4 = new javax.swing.JButton();
        tDeszyfrowany = new javax.swing.JTextField();
        tZaszyfrowany = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        tOdszyfrowany = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 10)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setText("\t\t\t\t\t\t\tSZYFR POLIALFABETYCZNY Z ALFABETEM MIESZANYM I KLUCZEM BIEZACYM\n* znaki specjalne nie są obsługiwane, spacje są pomijane\n* podczas odczytywania znaków po przekątnych następuje do momentu dotarcia do skrajnie prawego lub lewego znaku, następne znaki są czytane od miejsca kolejnego wiersza i kolumny, w której znajdowała się pierwsza litera przekątnej");
        jScrollPane1.setViewportView(jTextArea1);

        jLabel1.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 10)); // NOI18N
        jLabel1.setText("SZYFROWANIE");

        jLabel2.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 11)); // NOI18N
        jLabel2.setText("WPROWADŹ KLUCZ: ");

        tKlucz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tKluczActionPerformed(evt);
            }
        });

        bPrzeslij1.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 11)); // NOI18N
        bPrzeslij1.setText("PRZEŚLIJ");
        bPrzeslij1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPrzeslij1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 11)); // NOI18N
        jLabel3.setText("WPROWADŹ TEKST JAWNY");

        jLabel4.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 11)); // NOI18N
        jLabel4.setText("PODAJ ŚIEŻKĘ DO PLIKU Z TEKSTEM JAWNYM");

        jLabel5.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 11)); // NOI18N
        jLabel5.setText("LUB");

        bPrzeslij2.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 11)); // NOI18N
        bPrzeslij2.setText("PRZEŚLIJ");
        bPrzeslij2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPrzeslij2ActionPerformed(evt);
            }
        });

        bPrzeslij3.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 11)); // NOI18N
        bPrzeslij3.setText("PRZEŚLIJ");
        bPrzeslij3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPrzeslij3ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 11)); // NOI18N
        jLabel6.setText("SZYFROGRAM");

        bZapisz.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 11)); // NOI18N
        bZapisz.setText("ZAPISZ DO PLIKU");
        bZapisz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bZapiszActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 11)); // NOI18N
        jLabel7.setText("DESZYFROWANIE");

        bPrzeslij5.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 11)); // NOI18N
        bPrzeslij5.setText("ZAPISZ DO PLIKU");
        bPrzeslij5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPrzeslij5ActionPerformed(evt);
            }
        });

        bPrzeslij4.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 11)); // NOI18N
        bPrzeslij4.setText("ODSZYFRUJ SZYFROGRAM");
        bPrzeslij4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPrzeslij4ActionPerformed(evt);
            }
        });

        tDeszyfrowany.setToolTipText("");

        tZaszyfrowany.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tZaszyfrowanyActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 8)); // NOI18N
        jLabel8.setText("PODAJ ŚCIEŻKĘ DO PLIKU, W KÓRYM CHCESZ ZAPISAĆ SZYFROGRAM ->");

        tOdszyfrowany.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tOdszyfrowanyActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 8)); // NOI18N
        jLabel10.setText("PODAJ ŚCIEŻKĘ DO PLIKU, W KTÓRYM CHCESZ ZAPISAĆ DESZYFROWNY TEKST ->");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(119, 119, 119)
                .addComponent(bPrzeslij2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tSciezkaJ, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(78, 78, 78)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bPrzeslij3)
                                .addGap(61, 61, 61))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(tKlucz, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(bPrzeslij1))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(76, 76, 76)
                                        .addComponent(jLabel1))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(75, 75, 75)
                                        .addComponent(jLabel7)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(85, 85, 85))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(521, 521, 521)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(57, 57, 57))
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 9, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tDeszyfrowany, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                                    .addComponent(tSzyfrogram))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(68, 68, 68)
                                        .addComponent(jLabel8)
                                        .addGap(70, 70, 70))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(60, 60, 60)
                                        .addComponent(jLabel10)
                                        .addGap(38, 38, 38)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tOdszyfrowany, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tZaszyfrowany, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(94, 94, 94)
                                .addComponent(bPrzeslij4)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bZapisz, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(bPrzeslij5, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(62, 62, 62)
                    .addComponent(tJawny, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(879, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tKlucz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(bPrzeslij1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tSciezkaJ, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(bPrzeslij2)
                        .addComponent(bPrzeslij3)))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tSzyfrogram, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tZaszyfrowany, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(bZapisz)))
                .addGap(26, 26, 26)
                .addComponent(jLabel7)
                .addGap(38, 38, 38)
                .addComponent(bPrzeslij4)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tOdszyfrowany, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tDeszyfrowany, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(bPrzeslij5)
                        .addComponent(jLabel10)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(163, 163, 163)
                    .addComponent(tJawny, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(289, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tKluczActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tKluczActionPerformed
        
    }//GEN-LAST:event_tKluczActionPerformed

    private void bPrzeslij1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPrzeslij1ActionPerformed
        klucz = tKlucz.getText();
        klucz = klucz.replace(" ", "");
        
    }//GEN-LAST:event_bPrzeslij1ActionPerformed

    private void bPrzeslij2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPrzeslij2ActionPerformed
       jawny = tJawny.getText();
       jawny = jawny.replace(" ", "");
       
       szyfrogram = szyfrowanie().toString();
       szyfrogram = szyfrogram.replace('[',' ');
       szyfrogram = szyfrogram.replace(',',' ');
       szyfrogram = szyfrogram.replace(']',' ');
       tSzyfrogram.setText(szyfrogram);
    }//GEN-LAST:event_bPrzeslij2ActionPerformed

    private void bPrzeslij3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPrzeslij3ActionPerformed
       sciezkaJ = tSciezkaJ.getText();
       BufferedReader fileReader = null;
       
        
        
        try{
            
            fileReader = new BufferedReader(new FileReader(sciezkaJ));
           jawny = fileReader.readLine();
        } catch (FileNotFoundException ex) {
          Logger.getLogger(okno.class.getName()).log(Level.SEVERE, null, ex);
      }catch (Exception e){
            if(fileReader != null){
                try {
                    fileReader.close();
                } catch (IOException ex) {
                    Logger.getLogger(okno.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        jawny = jawny.replace(" ","");
        szyfrogram = szyfrowanie().toString();
        szyfrogram = szyfrogram.replace('[', ' ');
        szyfrogram = szyfrogram.replace(',',' ');
        szyfrogram = szyfrogram.replace(']',' ');
        tSzyfrogram.setText(szyfrogram);
       
       
       
    }//GEN-LAST:event_bPrzeslij3ActionPerformed

    private void bZapiszActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bZapiszActionPerformed
        szyfrogram = tSzyfrogram.getText();
        szyfrogram = szyfrogram.replace('[', ' ');
        szyfrogram = szyfrogram.replace(',',' ');
        szyfrogram = szyfrogram.replace(']',' ');     
        zaszyfrowany = tZaszyfrowany.getText();
      String filePath = zaszyfrowany;

FileWriter fileWriter = null;

try {
    fileWriter = new FileWriter(filePath);
    fileWriter.write(szyfrogram);
} catch(IOException e){
System.out.println("Error 1");
}
try{
    if (fileWriter != null) {
        fileWriter.close();
    }}catch(IOException ex){
        System.out.println("Error 2");
    }

    }//GEN-LAST:event_bZapiszActionPerformed

    private void bPrzeslij4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPrzeslij4ActionPerformed
        szyfrogram = tSzyfrogram.getText();
        szyfrogram = szyfrogram.replace('[', ' ');
        szyfrogram = szyfrogram.replace(',', ' ');
        szyfrogram = szyfrogram.replace(']', ' ');
        
        deszyfrowany = deszyfrowanie().toString();
        deszyfrowany = deszyfrowany.replace('[', ' ');
        deszyfrowany = deszyfrowany.replace(',', ' ');
        deszyfrowany = deszyfrowany.replace(']', ' ');
        
        tDeszyfrowany.setText(deszyfrowany);
        
        
    }//GEN-LAST:event_bPrzeslij4ActionPerformed

    private void bPrzeslij5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPrzeslij5ActionPerformed
        odszyfrowany = tOdszyfrowany.getText();
        deszyfrowany = tDeszyfrowany.getText();
        deszyfrowany = deszyfrowany.replace('[', ' ');
        deszyfrowany = deszyfrowany.replace(',', ' ');
        deszyfrowany = deszyfrowany.replace(']', ' ');        
      String filePath = odszyfrowany;

FileWriter fileWriter = null;

try {
    fileWriter = new FileWriter(filePath);
    fileWriter.write(deszyfrowany);
} catch(IOException e){
System.out.println("Error 1");
}
try{
    if (fileWriter != null) {
        fileWriter.close();
    }}catch(IOException ex){
        System.out.println("Error 2");
    }
    }//GEN-LAST:event_bPrzeslij5ActionPerformed

    private void tOdszyfrowanyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tOdszyfrowanyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tOdszyfrowanyActionPerformed

    private void tZaszyfrowanyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tZaszyfrowanyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tZaszyfrowanyActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(okno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(okno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(okno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(okno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new okno().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bPrzeslij1;
    private javax.swing.JButton bPrzeslij2;
    private javax.swing.JButton bPrzeslij3;
    private javax.swing.JButton bPrzeslij4;
    private javax.swing.JButton bPrzeslij5;
    private javax.swing.JButton bZapisz;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField tDeszyfrowany;
    private javax.swing.JTextField tJawny;
    private javax.swing.JTextField tKlucz;
    private javax.swing.JTextField tOdszyfrowany;
    private javax.swing.JTextField tSciezkaJ;
    private javax.swing.JTextField tSzyfrogram;
    private javax.swing.JTextField tZaszyfrowany;
    // End of variables declaration//GEN-END:variables
}
