import React, { useContext, useEffect, useRef, useState } from 'react';
import { database } from '../configs/Firebase';
import { MyUserContext } from '../App';
import { off, onValue, push, ref } from 'firebase/database';
import '../static/css/ChatWindow.css';

const ChatWindow = (props) => {
  const { id, name, onBack } = props;
  const [currentUser] = useContext(MyUserContext)
  const [message, setMessage] = useState('');
  const [messages, setMessages] = useState([]);
  const [currentUserId] = useState(currentUser.data.id)
  const messagesEndRef = useRef(null); 
  // Hàm để tạo ID duy nhất cho cuộc trò chuyện giữa hai người
  const getChatID = (userID1, userID2) => {
    let id = userID1 < userID2 ? `${userID1}_${userID2}` : `${userID2}_${userID1}`;
    return id
  };

  // Hàm để gửi tin nhắn
  const sendMessage = () => {
    if (message.trim() === '') return;

    const chatID = getChatID(currentUserId, id);
    const newMessage = {
      sender: currentUser.data.username,
      content: message,
      timestamp: Date.now(),
    };
    const messagesRef = ref(database, `chats/${chatID}`);

    // Lưu tin nhắn vào Firebase Realtime Database
    push(messagesRef,newMessage);

    setMessage(''); // Clear input
  };

  useEffect(() => {
    const chatID = getChatID(currentUserId, id);
    const messagesRef = ref(database,`chats/${chatID}`);

    onValue(messagesRef, (snapshot) => {
        const data = snapshot.val();
        const messagesList = data ? Object.values(data) : [];
        setMessages(messagesList);
       
        if (messagesEndRef.current) {
          messagesEndRef.current.scrollIntoView({ behavior: 'smooth' });
        }
    });

    return () => off(messagesRef); // Cleanup listener khi component unmount
}, [currentUserId,id]);
  return (
    <div
      style={{
        position: 'fixed',
        bottom: '80px',
        right: '20px', // Thay thế ô ChatList
        width: '300px',
        height: '400px',
        backgroundColor: '#fff',
        border: '1px solid #ccc',
        borderRadius: '10px',
        boxShadow: '0px 0px 10px rgba(0, 0, 0, 0.1)',
        zIndex: 1000,
        display: 'flex',
        flexDirection: 'column',
      }}
    >
      {/* Header chứa tên người dùng và nút Back */}
      <div
        style={{
          padding: '10px',
          backgroundColor: '#f1f1f1',
          borderBottom: '1px solid #ccc',
          fontWeight: 'bold',
          display: 'flex',
          justifyContent: 'space-between',
          alignItems: 'center',
        }}
      >
        <button onClick={onBack}
           style={{ marginRight: '10px' }}>Back</button>
        {name}
      </div>

      {/* Nội dung chat */}
      <div
        className='messages-container'
      >

        {/* Hiển thị danh sách tin nhắn */}
        {messages.map((message, index) => (
          <div key={index} className={`message ${message.sender === currentUser.data.username ? 'sent' : 'received'}`}>
            <p>{message.content}</p>
            <small>{new Date(message.timestamp).toLocaleTimeString()}</small>
          </div>
        ))}
         <div ref={messagesEndRef} />
      </div>

      {/* Ô nhập tin nhắn */}
      <div
        style={{
          padding: '10px',
          borderTop: '1px solid #ccc',
        }}
      >
        <input
          type="text"
          placeholder="Type a message"
          value={message}
          onChange={(e) => setMessage(e.target.value)}
          style={{
            width: '100%',
            padding: '8px',
            borderRadius: '5px',
            border: '1px solid #ccc',
          }}
        />
        <button onClick={sendMessage}>Gửi</button>
      </div>
    </div>
  );
};

export default ChatWindow;
