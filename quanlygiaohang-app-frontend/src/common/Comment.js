import { useEffect, useState } from "react";
import { authApi, endpoint } from "../configs/Apis";
import '../static/css/ProfileCard.css';
import { Button, Card, Form, Spinner } from "react-bootstrap";
const Comment = ({shipperId}) => {
    const [comments, setComment] = useState(null);
    const [userComment, setUserComment] = useState(null);
    const loadComment = async () => {
       
        try {
            let res;
            res = await authApi().get(endpoint['get-comments'](shipperId))
            setComment(res.data)
        }
        catch (e) { }

    };

    const addComment = async (e) => {
        e.preventDefault();
        let form = new FormData()
        form.append("comment", userComment)
        form.append("shipperId", shipperId)
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

    if (comments === null)
        return <Spinner animation="border" />;
    return (
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
    )
}
export default Comment