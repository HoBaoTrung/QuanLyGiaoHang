import { initializeApp } from 'firebase/app';
import { getDatabase } from 'firebase/database';
import { getAuth } from 'firebase/auth';
const firebaseConfig = {
  apiKey: "AIzaSyBiXnaa6SiOLEfn1yeKRr9eQ2SGNjIfFoo",
  databaseURL: "https://quan-ly-giao-hang-project-default-rtdb.asia-southeast1.firebasedatabase.app",
  authDomain: "quan-ly-giao-hang-project.firebaseapp.com",
  projectId: "quan-ly-giao-hang-project",
  storageBucket: "quan-ly-giao-hang-project.appspot.com",
  messagingSenderId: "524351729962",
  appId: "1:524351729962:web:aa251e05198571a49dae53"
};

// Khởi tạo Firebase app
const firebaseApp = initializeApp(firebaseConfig);

// Lấy Firebase Realtime Database
const database = getDatabase(firebaseApp);
const auth = getAuth(firebaseApp);
export { database, auth };