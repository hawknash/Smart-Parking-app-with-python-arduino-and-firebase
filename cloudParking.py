import httplib2
import serial
from firebase import firebase
firebase = firebase.FirebaseApplication('https://smart-parking-a335d.firebaseio.com',None)
ser = serial.Serial('COM4', 9600,timeout=2)
a=0    
while True:
    ch=(ser.readline().strip())
    r=list(ch)
    print(r)
    t=''
    
    for i in range(len(ch)):
        t+=chr(r[i])        
    s1=t.split(',')
    print(s1)            
    if len(s1)>1:
        if firebase.get('/final/s0',None)!=1:
            firebase.put('/python-example-f6d0b','s0',s1[0])
        if firebase.get('/final/s1',None)!=1:
            firebase.put('/python-example-f6d0b','s1',s1[1])
        if firebase.get('/final/s2',None)!=1:
            firebase.put('/python-example-f6d0b','s2',s1[2])
        if firebase.get('/final/s3',None)!=1:
            firebase.put('/python-example-f6d0b','s3',s1[3])
        if firebase.get('/final/s4',None)!=1:
            firebase.put('/python-example-f6d0b','s4',s1[4])
        if firebase.get('/final/s5',None)!=1:
            firebase.put('/python-example-f6d0b','s5',s1[5])
    
    
   
