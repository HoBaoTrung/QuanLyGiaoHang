import React, { useContext, useEffect, useState } from 'react';
import '../static/css/ProfileCard.css';
import '../static/css/StarRating.css';
import { Button, Card, Col, Form, Image, Row, Spinner } from 'react-bootstrap';
import { MyUserContext } from '../App';
import Apis, { authApi, endpoint } from '../configs/Apis';
import { useParams } from 'react-router-dom';
import Comment from '../common/Comment';


const ShipperDetail = () => {
    const [ownerRating, setOwnerRating] = useState(null);
    const [hoveredStar, setHoveredStar] = useState(null);
    const [averageReview, setAverageReview] = useState(null);
    const [user, setUser] = useState(null);
    const { shipperId } = useParams()
    const handleMouseEnter = (index) => {
        setHoveredStar(index);
    };

    const handleMouseLeave = () => {
        setHoveredStar(null);
    };

    const handleClick = async (index) => {
        setOwnerRating(index);
        let form = new FormData()
        form.append("shipperId", shipperId)
        form.append("point", index)
        try {

            let res = await authApi().post(endpoint['add-user-point'], form)
            console.info(res)
        }
        catch (e) { }
        loadRate()
    };

    const loadOwnerRating = async () => {

        let res;
        try {
            let form = new FormData()
            form.append("shipperId", shipperId)
            res = await authApi().post(endpoint['get-user-point'], form)
            setOwnerRating(res.data)

        }
        catch (e) { }

    };


    const loadShipper = async () => {

        let res;
        try {
            res = await authApi().get(endpoint['get-shipper'](shipperId))
            setUser(res.data)

        }
        catch (e) { }

    };

    const loadRate = async () => {

        let res;
        try {
            res = await Apis.get(endpoint['get-average-point'](shipperId))
            setAverageReview(res.data)

        }
        catch (e) { }

    };



    useEffect(() => {
        loadOwnerRating();
        loadRate(); loadShipper()
    }, []);


    if (user === null || averageReview === null)
        return <Spinner animation="border" />

    return (
        <Row className="mt-5">
            <Col md={5}>
                <Card className="profile-card m-3" style={{ height: "30rem" }}>
                    <Card.Body className="d-flex align-items-start">
                        <div>
                            <Image
                                src={user.avatar}
                                roundedCircle
                                className="profile-image"
                            />
                            <Button

                                className='mt-5'>Nhắn tin</Button>

                        </div>

                        <div className="ml-3">
                            <h4 className="profile-name">{user.username}</h4>
                            <p><strong>Email:</strong>{user.email}</p>
                            <p><strong>Số điện thoại:</strong> {user.phone}</p>
                            <p><strong>Vai trò:</strong> SHIPPER</p>
                            <p><strong>Đánh giá trung bình:</strong> {averageReview} </p>

                            <div>
                                <p className='mb-0'><strong>Đánh giá của bạn:</strong></p>
                                <div className="star-rating">
                                    {[...Array(5)].map((_, index) => {
                                        const starIndex = index + 1;
                                        return (
                                            <span
                                                key={starIndex}

                                                className={`star ${starIndex <= (hoveredStar || ownerRating) ? 'filled' : ''}`}
                                                onMouseEnter={() => handleMouseEnter(starIndex)}
                                                onMouseLeave={handleMouseLeave}
                                                onClick={() => handleClick(starIndex)}
                                            >
                                                ★
                                            </span>
                                        );
                                    })}
                                </div>

                            </div>


                        </div>
                    </Card.Body>

                </Card>
            </Col>

            < Col md={7}>
                <Comment shipperId={shipperId} />
            </Col>

        </Row >
    );
}
export default ShipperDetail;