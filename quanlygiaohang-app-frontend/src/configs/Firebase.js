import { getDatabase } from 'firebase/database';
import { getAuth } from 'firebase/auth';
// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
const firebaseConfig = {
  apiKey: "AIzaSyBiXnaa6SiOLEfn1yeKRr9eQ2SGNjIfFoo",
  authDomain: "quan-ly-giao-hang-project.firebaseapp.com",
  databaseURL: "https://quan-ly-giao-hang-project-default-rtdb.asia-southeast1.firebasedatabase.app",
  projectId: "quan-ly-giao-hang-project",
  storageBucket: "quan-ly-giao-hang-project.appspot.com",
  messagingSenderId: "524351729962",
  appId: "1:524351729962:web:aa251e05198571a49dae53"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);

// Lấy Firebase Realtime Database
const database = getDatabase(app);
const auth = getAuth(app);
export { database, auth };