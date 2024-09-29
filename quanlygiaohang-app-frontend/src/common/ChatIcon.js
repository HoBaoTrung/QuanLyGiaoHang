import React, { useContext, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import ChatList from './ChatList';
import { MyUserContext } from '../App';
import { FaRegComment } from 'react-icons/fa';
import ChatWindow from './ChatWindow';


const ChatIcon = () => {
    const [user] = useContext(MyUserContext)
    const [showChatList, setShowChatList] = useState(false);
    const [selectedUser, setSelectedUser] = useState(null);
    const navigate = useNavigate();

    const openChatList = () => {
        if (user !== null) {
          
            setSelectedUser(null);
            // Nếu đã đăng nhập, bật/tắt hiển thị ô Chat List
            setShowChatList(!showChatList);
        } else {
            // Nếu chưa đăng nhập, chuyển tới trang Login
            navigate('/login');
        }
    };
    const selectUser = (user) => {
        
        setSelectedUser(user); // Chọn người dùng để bắt đầu chat
        setShowChatList(false); // Đóng danh sách chat sau khi chọn người dùng
    };

    const handleBack = () => {
        setSelectedUser(null);  // Quay lại danh sách chat
        setShowChatList(true); 
      };

    return (
        <div>
            {/* Biểu tượng chat */}
            <div
                onClick={openChatList}
                style={{
                    width: '50px',
                    height: '50px',
                    borderRadius: '50%',
                    backgroundColor: '#6fed0c',
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'center',
                    cursor: 'pointer',
                    position: 'fixed',
                    bottom: '20px',
                    right: '20px',
                    zIndex: 1000,
                    boxShadow: '0px 0px 10px rgba(0, 0, 255, 0.3)',
                }}
            >
                <FaRegComment />
            </div>

            {/* Hiển thị ô ChatList nếu showChatList === true */}
            {/* {showChatList && <ChatList />} */}
            {showChatList && user && <ChatList onSelectUser={selectUser}/>}
            {/* Hiển thị cửa sổ chat nếu có người dùng được chọn */}
            {selectedUser && user && <ChatWindow id={selectedUser.shipperId} name={selectedUser.shipperName}  onBack={handleBack} />}
        </div>
    );
};

export default ChatIcon;
