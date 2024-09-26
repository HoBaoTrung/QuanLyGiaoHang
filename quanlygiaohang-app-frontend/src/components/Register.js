import React, { useEffect, useRef, useState } from 'react';
import '../static/css/Register.css';
import image from '../static/image/image.png';
import { Row, Col, Form, Button, Spinner } from 'react-bootstrap';
import { Link, useNavigate } from "react-router-dom"
import * as Validator from '../configs/Validator';
import Apis, { endpoint } from '../configs/Apis';
import UploadImage from '../common/UploadImage';
const Register = () => {
    const nav = useNavigate()

    const img = useRef(null)
    const [avatar, setAvatar] = useState(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const handleImageChange = (image) => {
        img.current = image;
    };
    const closeModal = () => {
        setIsModalOpen(false);
    };

    const [isChecked, setIsChecked] = useState(false);
    const handleCheckboxChange = (event) => {
        setIsChecked(event.target.checked);
    };

    const changeField = (e, field) => {
        setUser(current => {
            return { ...current, [field]: e.target.value }
        })
    }


    const [loading, setLoading] = useState(false)

    const [user, setUser] = useState({
        "phone": "",
        "email": "",
        'username': '',
        "password": '',
        'confirmPassword': "",
        'cmnd': ""
    })



    const [msgs, setMsgs] = useState({
        "errCCCD": "",
        "errUsername": "",
        'errPassword': '',
        'errConfirmPassword': '',
        "errPhone": '',
        'errMail': null
    });

    useEffect(() => {

        // Chạy mã khi msgs thay đổi
        console.log('Value of errCCCD has changed:', msgs);

    }, [msgs]);



    const updateMessage = async () => {
        // Sử dụng hàm callback để đảm bảo trạng thái được cập nhật chính xác
        setMsgs(prevMsgs => {
            // prevMsgs là trạng thái hiện tại trước khi cập nhật
            // Tạo một đối tượng trạng thái mới với giá trị errCCCD được cập nhật
            const updatedMsgs = {
                ...prevMsgs, // Sao chép các thuộc tính hiện tại của trạng thái
                errConfirmPassword: Validator.validateConfirmPassword(user.password, user.confirmPassword),
                errPassword: Validator.validatePassword(user.password),
                errPhone: Validator.validatePhone(user.phone),
                errUsername: Validator.validateUsername(user.username),
                errCCCD: Validator.validateCCCD(user.cmnd)
            };
            // Trả về trạng thái mới để setMsgs cập nhật
            return updatedMsgs;
        });
    };


    const register = async (event) => {
        event.preventDefault();

        const process = async () => {

            let form = new FormData()
            for (let i in user)
                if (i !== "confirmPassword")
                    form.append(i, user[i])
            form.append('avatar', img.current)

            setLoading(true)
            try {

                const res = await Apis.post(endpoint['register'], form);
                if (res.status === 201) {
                    nav("/login");
                }

            }
            catch (error) {

                if (error.response && error.response.status === 400) {

                    setMsgs(prevState => ({
                        ...prevState,
                        errPhone: error.response.data.phone || null,
                        errUsername: error.response.data.username || null,
                        errCCCD: error.response.data.cmnd || null,
                        errMail: error.response.data.email || null
                    }));
                    setLoading(false)

                }
            }

        }
        const cmndValue = user.cmnd;
        if (!isChecked) {
            //xóa trường cmnd nếu checkbox không được check
            user.cmnd = '';
        }
        else {
            setUser(current => {
                return { ...current, cmnd: cmndValue }
            })

            if (!user.cmnd || img.current===null) {
                alert('Nếu bạn đăng ký là shipper, vui lòng nhập CMND/CCD và thêm ảnh đại diện!');
                return;
            }
        }

        let hasNonNullValue = false;
        updateMessage()
        console.info('hasNonNullValue')
        for (const key in msgs) {
            console.info(key + ":")
            console.info(msgs[key])
            if (msgs[key] !== null) {
                hasNonNullValue = true;
                break; // Ngưng kiểm tra khi đã tìm thấy giá trị khác null
            }
        }



        if (hasNonNullValue === false) {// không có thông báo lỗi
            //    setLoading(true)

            process()
        }


    };



    return (

        <Row noGutters fluid  >
            <Col className="left-column p-0" xs={12} md={5} >
                <img src={image}
                    alt="Register" />
            </Col>
            <Col xs={12} md={7} >

                <Form onSubmit={register} className='mt-1'>
                    <div >
                        <h2 className='text-center'>TẠO TÀI KHOẢN</h2>
                        <h4 className='text-center' style={{ color: 'orange' }}>Chúng tôi luôn đồng hành cùng bạn</h4>
                    </div>
                    <Row>
                        <Col md={6}>
                            <Form.Group className="mb-3" >
                                <Form.Label>
                                    Số điện thoại
                                </Form.Label>

                                <Form.Control required='true'
                                    value={user.phone} onChange={e => changeField(e, "phone")} type="tel" placeholder="Nhập số điện thoại" />
                                <div class="text-danger">{msgs.errPhone}</div>
                            </Form.Group>

                            <Form.Group className="mb-3" >
                                <Form.Label>
                                    Email
                                </Form.Label>

                                <Form.Control required='true'
                                    value={user.email} onChange={e => changeField(e, "email")} type="email" placeholder="Nhập Email" />
                                <div class="text-danger">{msgs.errMail}</div>
                            </Form.Group>

                            <Form.Group className="mb-3" >
                                <Form.Label>
                                    Tên tài khoản
                                </Form.Label>

                                <Form.Control required='true' value={user.username}
                                    onChange={e => changeField(e, "username")} type="text" placeholder="Tên tài khoản" />
                                <div class="text-danger">{msgs.errUsername}</div>
                            </Form.Group>
                            <Form.Group className="mb-3" >
                                <Form.Label>
                                    Mật khẩu
                                </Form.Label>

                                <Form.Control value={user.password} onChange={e => changeField(e, "password")}
                                    type="password" placeholder="Tối thiểu 6 ký tự bao gồm chữ cái và số" required='true' />
                                <div class="text-danger">{msgs.errPassword}</div>
                            </Form.Group>
                            <Form.Group className="mb-3" >
                                <Form.Label>
                                    Nhập lại mật khẩu
                                </Form.Label>

                                <Form.Control value={user.confirmPassword} onChange={e => changeField(e, "confirmPassword")}
                                    type="password" placeholder="Tối thiểu 6 ký tự bao gồm chữ cái và số" required='true' />
                                <div class="text-danger">{msgs.errConfirmPassword}</div>
                            </Form.Group>

                            <Form.Group className="mb-3">
                                {loading === true ? <Spinner animation="border" /> :
                                    <Button type="submit">Đăng ký</Button>}
                            </Form.Group>
                            <h4>
                                Bạn đã có tài khoản?
                                <Link to="/login">Đăng nhập</Link>
                            </h4>
                        </Col>

                        <Col md={6}>

                            <Form.Group controlId="formBasicCheckbox" className="mb-2">
                                <Form.Check
                                    type="checkbox"
                                    label="Đăng ký shipper"
                                    checked={isChecked}
                                    onChange={handleCheckboxChange}
                                />
                            </Form.Group>


                            <Form.Group controlId="formField1" className="mb-3">
                                <Form.Control
                                    placeholder="Hãy nhập CMND/CCCD"
                                    type="text"
                                    value={user.cmnd}
                                    onChange={e => changeField(e, "cmnd")}
                                    required={isChecked}
                                    disabled={!isChecked}
                                />
                                {isChecked ? <div class="text-danger">{msgs.errCCCD}</div> : <></>}

                            </Form.Group>
                            <UploadImage onImageChange={handleImageChange} />


                        </Col>


                    </Row>


                </Form>
            </Col>
        </Row>




    );
}
export default Register;