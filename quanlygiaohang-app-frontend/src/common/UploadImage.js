import { useRef, useState } from "react";
import { Form } from "react-bootstrap";
import '../static/css/CreateOrder.css';
const UploadImage = ({ onImageChange }) => {
    const img = useRef(null)

    const [avatar, setAvatar] = useState(null);
   

    const handleImageChange = (event) => {
        const file = event.target.files[0];
        if (file) {

            const reader = new FileReader();
            reader.onload = (e) => {
                setAvatar(e.target.result);
                onImageChange(img.current.files[0]);
            };
            reader.readAsDataURL(file);

        }

    };


    return (
        <div class="form-floatingmb-3" >
            
                <input type="button" class="btn btn-primary" onClick={() => document.getElementById('imageInput').click()}
                    value="Chọn ảnh"
                />
                <Form.Control type="file" accept="image/*" onChange={handleImageChange} style={{ display: 'none' }}
                    id="imageInput" ref={img} />

                <div
                    onClick={() => document.getElementById('imageInput').click()}
                    className="p-0 ticket mt-3 "
                    style={{ cursor: "pointer", }}

                >
                    {avatar ? (
                        <img
                            src={avatar}
                            alt="Selected"
                            style={{
                                cursor: "pointer",
                                objectFit: 'contain',
                                maxWidth: '100%',
                                maxHeight: '100%',

                            }}
                        />
                    ) : (
                        <p>Click để chọn hình ảnh</p>
                    )}
                </div>

        </div>
    )

}
export default UploadImage