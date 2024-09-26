import React, { useContext, useEffect, useRef, useState } from 'react';
import '../static/css/ProfileCard.css';
import '../static/css/StarRating.css';
import { Button, Card, Col, Form, Image, Row, Spinner } from 'react-bootstrap';
import { MyUserContext } from '../App';
import Apis, { authApi, endpoint } from '../configs/Apis';
import ShipperInfo from '../common/ShipperInfo';

const Profile = () => {
    const [userComment, setUserComment] = useState(null);
    const [averageReview, setAverageReview] = useState(null);
    const [user,] = useContext(MyUserContext);
    const [comments, setComment] = useState(null);

    const loadaverageReview = async () => {
        console.info(user)
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
        loadComment();
    }, []);
    useEffect(() => {
        loadaverageReview();
    }, []);

    if (comments === null && user.data.shipperId !== null)
        return <Spinner animation="border" />;

    return (
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
                                {/* <p><strong>Đánh giá trung bình:</strong> {averageReview} </p> */}
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
    );
}
export default Profile;