
import React, { useContext, useEffect, useState } from 'react';
import { MyUserContext } from '../App';
import { onValue, ref } from 'firebase/database';
import { auth, database } from '../configs/Firebase';
import { authApi, endpoint } from '../configs/Apis';
import MySpinner from '../layout/Spinner';

const ChatList = ({ onSelectUser }) => {
    const [currentUser] = useContext(MyUserContext)
    const [userId] = useState( currentUser.data.id)
    const [chatUsers, setChatUsers] = useState(null);
    const [loading, setLoading] = useState(true);



    useEffect(() => {
        setLoading(true);

        const chatsRef = ref(database, '/chats'); // Tham chiếu đến "chats" trên Firebase Database

        onValue(chatsRef, (snapshot) => {
            const data = snapshot.val();
            const users = [];

            if (data === null) {
                setChatUsers([]);
                setLoading(false);
                return;
            }


            if (data) {
                // Lặp qua từng cuộc trò chuyện (ví dụ: 1_6, 2_6,...)
                for (const chatKey in data) {

                    const [first, second] = chatKey.split('_');

                    if (first != userId && second != userId) continue
                 
                   
                    const chat = data[chatKey];

                    // Lấy chat cuối cùng của đoạn
                    let lastMessage = Object.values(chat).pop();
                    //  if (first != userId) setShipperId(first)
                    // else setShipperId(second)
                    lastMessage.shipperId= first != userId?first:second
                    if (lastMessage) {
                        users.push(lastMessage);
                    }
                }
                users.sort((a, b) => b.timestamp - a.timestamp);
                setChatUsers(users); // Cập nhật danh sách người dùng chat
            } else {
                setChatUsers([]); // Nếu không có dữ liệu, đặt danh sách người dùng rỗng
            }
            setLoading(false); // Kết thúc quá trình load
        });


    }, [userId]);

    if(loading) return <MySpinner/>

    return (
        <div
            style={{
                position: 'fixed',
                bottom: '80px',
                right: '20px',
                width: '300px',
                height: '400px',
                backgroundColor: '#fff',
                border: '1px solid #ccc',
                borderRadius: '10px',
                boxShadow: '0px 0px 10px rgba(0, 0, 0, 0.1)',
                overflowY: 'auto',
                zIndex: 1000,
            }}
        >
            <div
                style={{
                    padding: '10px',
                    borderBottom: '1px solid #ccc',
                    backgroundColor: '#f1f1f1',
                    fontWeight: 'bold',
                    textAlign: 'center',
                }}
            >
                Chat List
            </div>

            <ul style={{ listStyle: 'none', padding: 0, margin: 0 }}>
                {chatUsers && chatUsers.map(user => (
                    <li
                        key={user.id}
                        style={{
                            padding: '10px',
                            borderBottom: '1px solid #eee',
                            cursor: 'pointer',
                            display: 'flex',
                            justifyContent: 'space-between',
                            alignItems: 'center',
                        }}
                        onClick={async() => {
                           let res = await authApi().get(endpoint['get-shipper'](user.shipperId))
                            user.shipperName=res.data.username
                            onSelectUser(user)
                        }}
                    >

                        <span>{user.sender == currentUser.data.username ? "You" : user.sender}</span>
                        <span style={{ fontStyle: 'italic', color: '#aaa' }}>
                            {user.content}
                        </span>

                        <div style={{ fontStyle: 'italic', color: '#aaa' }}>
                            {new Date(user.timestamp).toLocaleString()} {/* Hiển thị timestamp */}
                        </div>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default ChatList;
