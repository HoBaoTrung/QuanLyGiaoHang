import React, { useContext, useEffect, useRef, useState } from 'react';
import '../static/css/ProfileCard.css';
import '../static/css/StarRating.css';
import { Button, Card, Col, Form, Image, Row, Spinner } from 'react-bootstrap';
import { MyUserContext } from '../App';
import Apis, { authApi, endpoint } from '../configs/Apis';
import ShipperInfo from '../common/ShipperInfo';
import UploadImage from '../common/UploadImage';
import * as Validator from '../configs/Validator';
import { useNavigate } from 'react-router-dom';
const Profile = () => {
    const [userComment, setUserComment] = useState(null);
    const [averageReview, setAverageReview] = useState(null);
    const [user, dispatch] = useContext(MyUserContext);
    const [isShipper] = useState(user.data.roles.some(item => item.name === 'SHIPPER'));
    const [comments, setComment] = useState(null);
    const img = useRef(null)
    const nav = useNavigate()
    const [cmnd, setCMND] = useState("")
    const [newInfo, setNewInfo] = useState({
        "confirmNewPassword": "",
        "newPassword": "",
        "email": "",
        "phone": "",

    });
    const [msgs, setMsgs] = useState({
        "errCCCD": "",
        "errUsername": "",
        'errPassword': '',
        'errConfirmPassword': '',
        "errPhone": '',
        'errMail': null
    });

    const updateMessage = async () => {
        // Sử dụng hàm callback để đảm bảo trạng thái được cập nhật chính xác
        setMsgs(prevMsgs => {
            // prevMsgs là trạng thái hiện tại trước khi cập nhật
            // Tạo một đối tượng trạng thái mới với giá trị errCCCD được cập nhật
            const updatedMsgs = {
                ...prevMsgs, // Sao chép các thuộc tính hiện tại của trạng thái
                errConfirmPassword: Validator.validateConfirmPassword(newInfo.newPassword, newInfo.confirmNewPassword),
                errPassword: Validator.validatePassword(newInfo.newPassword),
                errPhone: Validator.validatePhone(newInfo.phone),
                errCCCD: Validator.validateCCCD(newInfo.cmnd)
            };
            // Trả về trạng thái mới để setMsgs cập nhật
            return updatedMsgs;
        });
    };

    const loadaverageReview = async () => {

        let res;
        try {
            if (user.data.shipperId !== null) {
                res = await Apis.get(endpoint['get-average-point'](user.data.shipperId))
                setAverageReview(res.data)
            }
        }
        catch (e) { }

    };

    const loadComment = async () => {

        let res;
        try {
            if (user.data.shipperId !== null) {
                res = await authApi().get(endpoint['get-comments'](user.data.shipperId))
                setComment(res.data)
            }

        }
        catch (e) { }

    };

    const change = (evt, field) => {
        setNewInfo(current => {
            return { ...current, [field]: evt.target.value }
        })
    }

    const addOrUpdateShipper = async (e) => {
        e.preventDefault();
        let form = new FormData()
        form.append("cmnd", cmnd)
        form.append("isUpdate", isShipper)
        if (img)
            form.append("avatar", img.current);

        let url = `${endpoint['add-update-shipper']}`
        console.log(img)
        console.info(form.get("avatar"))
        try {

            
            if (form.get("avatar") !== 'null' || form.get("cmnd").length > 0) {
                console.log("img")
                let res = await authApi().post(url, form)
            }
        
        } catch (error) {
            if (error.response && error.response.status === 400) {

                setMsgs(prevState => ({
                    ...prevState,
                    errCCCD: error.response.data.cmnd || null,

                }));
            }
        }
    }

    const addComment = async (e) => {
        e.preventDefault();
        let form = new FormData()
        form.append("comment", userComment)
        form.append("shipperId", user.data.shipperId)
        let res;
        try {

            res = await authApi().post(endpoint['add-comments'], form)
            loadComment();

        }
        catch (e) { }
        setUserComment("")
    };

    useEffect(() => {
        loadComment(); loadaverageReview();
    }, []);


    const update = async () => {
        let form = new FormData();

        if (newInfo.newPassword && newInfo.newPassword.trim().length !== 0) {
            if (newInfo.newPassword === newInfo.confirmNewPassword)
                form.append("newPassword", newInfo.newPassword)
        }

        for (let field in newInfo) {

            if (field !== "confirmNewPassword" && field !== 'newPassword')
                form.append(field, newInfo[field]);
        }
        if (!img)
            form.append("avatar", img.current.files[0]);



        try {
            let res = await authApi().patch(endpoint['update-user'], form)

        } catch (error) {

            if (error.response && error.response.status === 400) {

                setMsgs(prevState => ({
                    ...prevState,
                    errPhone: error.response.data.phone || null,
                    errUsername: error.response.data.username || null,
                    errMail: error.response.data.email || null
                }));


            }
        }


    }

    if (comments === null && user.data.shipperId !== null) { return <Spinner animation="border" />; }

    return (
        <>
            <Row className="mt-5">

                <Col md={5}>
                    <Card className="profile-card m-3" style={{ height: "30rem" }}>
                        <Card.Body className="d-flex align-items-start">
                            <Image
                                src={user.data.avatar} // URL ảnh đại diện hoặc thay bằng ảnh thật
                                roundedCircle
                                className="profile-image"
                            />
                            <div className="ml-3">
                                {user.data.shipperId === null ? <>
                                    <h4 className="profile-name">{user.data.username}</h4>
                                    <p><strong>Email:</strong>{user.data.email}</p>
                                    <p><strong>Số điện thoại:</strong> {user.data.phone}</p>
                                    <p><strong>Vai trò:</strong>KHÁCH HÀNG</p>
                                </> : <>

                                    <ShipperInfo shipperId={user.data.shipperId} />

                                </>}
                            </div>
                        </Card.Body>

                    </Card>
                </Col>
                {user.data.shipperId !== null ?

                    < Col md={7}>
                        <Card className="comment-card m-3" style={{ height: "30rem" }}>
                            <Card.Body>
                                <h4 className="mb-4">Bình luận</h4>
                                {/* Form nhập bình luận */}
                                <Form onSubmit={addComment}>
                                    <Form.Group controlId="commentForm">
                                        <Form.Label>Nhập bình luận của bạn</Form.Label>
                                        <Form.Control as="textarea" value={userComment} rows={3} placeholder="Viết bình luận..."
                                            onChange={(e) => { setUserComment(e.target.value) }}
                                        />
                                    </Form.Group>
                                    <Button variant="primary" className='mt-2' type="submit">Gửi bình luận</Button>
                                </Form>

                                {/* Phần hiển thị bình luận */}
                                <div className="mt-4" >
                                    <h5>Các bình luận:</h5>
                                    <div className="comment-list">

                                        {comments.map(c =>
                                            <p className='mt-1 text-white p-1' style={{ backgroundColor: "#3a3b3c" }}>
                                                <strong>{c.username}:</strong> {c.comment}</p>
                                        )}

                                    </div>
                                </div>
                            </Card.Body>
                        </Card>

                    </Col>
                    : <></>}

            </Row >
            <div>
                <Row>
                    <Col md={6}>
                        <Card className="comment-card m-3" style={{ height: "30rem", overflowY: "auto" }} >
                            <Card.Body>
                                <h4 className="mb-4">Chỉnh sửa thông tin</h4>
                                {/* Form nhập bình luận */}
                                <Form onSubmit={update} >

                                    <Form.Group className='mb-2'>
                                        <Form.Label>Email</Form.Label>
                                        <Form.Control type="email" value={userComment} rows={3} placeholder="New Email..."
                                            onChange={(e) => change(e, "email")}
                                        />
                                        <div class="text-danger">{msgs.errMail}</div>
                                    </Form.Group>

                                    <Form.Group className='mb-2'>
                                        <Form.Label>Phone number</Form.Label>
                                        <Form.Control pattern="[0-9]{4}[0-9]{3}[0-9]{3}" title="Vui lòng nhập đúng định dạng số điện thoại"
                                            type="text" value={userComment} rows={3} placeholder="New Email..."
                                            onChange={(e) => change(e, "phone")}
                                        />
                                        <div class="text-danger">{msgs.errPhone}</div>
                                    </Form.Group>

                                    <Form.Group className='mb-2'>
                                        <Form.Label>New password</Form.Label>
                                        <Form.Control type="password" value={userComment} rows={3} placeholder="New password..."
                                            onChange={(e) => change(e, "newPassword")}
                                        />
                                    </Form.Group>

                                    <Form.Group className='mb-2'>
                                        <Form.Label>Confirm new password</Form.Label>
                                        <Form.Control type="password" value={userComment} rows={3} placeholder="Confirm new password..."
                                            onChange={(e) => change(e, "confirmNewPassword")}
                                        />
                                    </Form.Group>


                                    <Button variant="danger" className='mt-2' type="submit">Cập nhật</Button>
                                </Form>

                            </Card.Body>
                        </Card>
                    </Col>
                    <Col md={6}>

                        <Card className="comment-card m-3" style={{ height: "60%", overflowY: "auto" }} >
                            <Card.Body>
                                <h4 className="mb-4">{isShipper ? <>Chỉnh sửa thông tin</> :
                                    <>Đăng ký shipper</>}  </h4>

                                <Form onSubmit={addOrUpdateShipper} >

                                    <Form.Group controlId="infoForm">
                                        <Form.Label>CCCD</Form.Label>
                                        <Form.Control
                                            pattern="[0-9]{4}[0-9]{4}[0-9]{4}" title="Vui lòng nhập đúng định dạng số CCCD"
                                            type="text" rows={3} placeholder="CCCD..."
                                            onChange={(e) => { setCMND(e.target.value) }}
                                        />
                                        <div class="text-danger">{msgs.errCCCD}</div>
                                    </Form.Group>

                                    <Button variant="success" className='mt-2' type="submit">{isShipper ? 'Cập nhật' : "Đăng ký"}</Button>
                                </Form>

                            </Card.Body>

                        </Card>
                        <Card className="comment-card m-3" >
                            <UploadImage onImageChange={(image) => {
                                img.current = image;
                            }} /></Card>
                    </Col>
                </Row>
            </div>
        </>
    );
}
export default Profile;