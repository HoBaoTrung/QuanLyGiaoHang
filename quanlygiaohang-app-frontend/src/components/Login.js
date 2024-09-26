import React, { useContext, useEffect, useState } from 'react';
import '../static/css/Register.css';
import image from '../static/image/image.png';
import { Container, Row, Col, Form, Button, Spinner } from 'react-bootstrap';
import { Link, Navigate, useNavigate } from "react-router-dom"
import { MyUserContext } from "../App";
import cookies from 'react-cookies'
import Apis, { authApi, endpoint } from '../configs/Apis';
import { getAuth, signInWithCustomToken } from 'firebase/auth';
const Login = () => {
    const nav = useNavigate()

    const [user, dispatch] = useContext(MyUserContext)
    const [username, setUsername] = useState();
    const [password, setPassword] = useState();
    const [err, setErr] = useState(null)
    const [loading, setLoading] = useState(false)

    const login = (evt) => {
        evt.preventDefault();
        const process = async () => {
            try {
                setLoading(true)
                let res = await Apis.post(endpoint['login'], {
                    'username': username,
                    'password': password
                })
                let token = res.data

                cookies.save('token', token)
                let data = await authApi().get(endpoint['current-user']);

                cookies.save('user', data)

                dispatch({
                    'type': 'login',
                    'payload': data
                })


            } catch (error) {
                setErr("Tài khoản hoặc mật khẩu không đúng")
                setLoading(false)
            }
           
        }
        process()
        
       

    }

    const authenticateWithFirebase = async () => {
        try {
           
          // Gửi JWT token lên Spring Boot để lấy Firebase custom token
          const response = await authApi().get(endpoint['get-firebase-token'])

          const  firebaseToken  = response.data;
          
          // Đăng nhập vào Firebase bằng custom token
          const auth = getAuth();
          await signInWithCustomToken(auth, firebaseToken);
          console.log("Đăng nhập Firebase thành công!");
        } catch (error) {
          console.error("Lỗi khi xác thực Firebase", error);
        }
      };

    if (user !== null){
        authenticateWithFirebase();
        return <Navigate to="/" />
    } 

   

    return (

        <Row noGutters fluid  >
            <Col className="left-column p-0" xs={12} md={5} >
                <img src={image}
                    alt="image" />
            </Col>
            <Col xs={12} md={7} >

                <Form onSubmit={login} className='mt-1'>
                    <div>
                        <h2 className='text-center'>ĐĂNG NHẬP</h2>
                        <h4 className='text-center' style={{ color: 'orange' }}>Chúng tôi luôn đồng hành cùng bạn</h4>
                    </div>

                    <Container>
                        <Form.Group className="mb-3" >
                            <Form.Label>
                                Tên tài khoản
                            </Form.Label>
                            <Form.Control className="w-50" value={username} onChange={e => setUsername(e.target.value)} type="text" placeholder="Tên tài khoản" />

                        </Form.Group>
                        <Form.Group className="mb-3" >
                            <Form.Label>
                                Mật khẩu
                            </Form.Label>

                            <Form.Control className="w-50" value={password} onChange={e => setPassword(e.target.value)}
                                type="password" placeholder="Tối thiểu 6 ký tự bao gồm chữ cái và số" />

                        </Form.Group>


                        <Form.Group className="mb-3">
                            {loading === true ? <Spinner animation="border" /> :
                                <Button type="submit">Đăng nhập</Button>}
                        </Form.Group>


                        <h4>
                            Bạn chưa có tài khoản?
                            <Link to="/register">Đăng kí</Link>
                        </h4>
                    </Container>
                </Form>
            </Col>
        </Row>

    );
}
export default Login;