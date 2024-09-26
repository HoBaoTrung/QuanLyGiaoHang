import { useEffect, useState, useCallback, useContext } from "react"
import { Button, Col, Container, Form, Image, Row, Spinner } from "react-bootstrap"
import { authApi, endpoint } from "../configs/Apis"
import { useParams, useLocation } from "react-router-dom"
import { MyUserContext } from "../App"


const ProductViewForShipper = () => {
    const { productId } = useParams()
    const [product, setProduct] = useState(null);
    const [error, setError] = useState("");
    const [inputValue, setInputValue] = useState("");
    const [user, ] = useContext(MyUserContext);
    const loadProduct = useCallback(async () => {

        try {
            const res = await authApi().get(endpoint['get-orderDetail-for-shipper'](productId));
            console.info(res.data)
            setProduct(res.data)

        }
        catch (ex) { console.error(ex) }
    }, []);

    const handleInputChange = (e) => {
        const value = e.target.value;

        if (value < product.basicPrice) {
            setError("Giá phải cao hơn hoặc bằng giá khởi điểm");
        } else {
            setInputValue(value);
            setError(""); // Xóa thông báo lỗi khi giá trị hợp lệ
        }
    };

    useEffect(() => {

        loadProduct();
    }, [loadProduct]);

    const formatter = new Intl.NumberFormat('vi-VN', {
        style: 'decimal',
        minimumFractionDigits: 0, // Không cần số thập phân
        maximumFractionDigits: 0  // Không cần số thập phân
    });

    const submitDauGia = (e) => {
        e.preventDefault();
       
        let form = new FormData()
        form.append("productId", product.id)
        form.append("price", inputValue)
        form.append("userId", user.data.id)
        let res = authApi().post(endpoint['add-or-update-daugia'], form)
    };

    return (
        <>
            <h1 className="text-center text-info">CHI TIẾT SẢN PHẨM</h1>

            {product === null ? <Spinner animation="border" /> : <>
                <Container>
                    <Row>
                        <Col md={7} xs={6}>
                            <Image src={product.image} style={{ width: '35rem' }} />
                            <div className="mt-3">
                                <h2>Giá khởi điểm: {formatter.format(product.basicPrice)}đ</h2>
                                <Form onSubmit={submitDauGia}>
                                    <Form.Group className="d-flex align-items-center">
                                        <Form.Label htmlFor="price" className="me-2 mr-2">Ra giá:</Form.Label>
                                        <Form.Control
                                            type="number"
                                            id="price"

                                            style={{ width: "200px" }}
                                            min={product.basicPrice}
                                            required
                                            placeholder="Nhập giá"
                                            isInvalid={!!error} // Hiển thị viền đỏ nếu có lỗi
                                            onInput={handleInputChange}  // Reset lỗi khi người dùng bắt đầu nhập

                                        />
                                        <Button type="submit" variant="info" className="ms-3 ml-2">RA GIÁ</Button>
                                    </Form.Group>
                                    {error && <p className="text-danger mt-2">{error}</p>}
                                </Form>
                            </div>

                        </Col>
                        <Col md={5} xs={6}>
                            <h2>Thông tin đơn hàng:<br /> </h2>
                            <h5 className="text-success">Tên: {product.name}</h5>
                            <h5>Mô tả: {product.note}</h5>
                            <h5>Giao từ: {product.fromDistrictId.districtName}, {product.fromDistrictId.provinceId.provinceName}</h5>
                            <h5>Giao đến: {product.toDistrictId.districtName}, {product.toDistrictId.provinceId.provinceName}</h5>
                            <h5>Số lượng: {product.quantity}</h5>
                            <h5>Dịch vụ: {product.serviceId.name}</h5>
                            <h5>Lưu ý: </h5>
                            <div style={{
                                width: '100%', height: "5rem", border: "2px solid orange", display: 'flex',
                                flexWrap: 'wrap'
                            }}>
                                {product.categorySet.map(category =>
                                    <div key={category.id} style={{
                                        display: 'inline-block', // Đảm bảo khung viền giãn theo chiều rộng của chữ
                                        border: '1px solid #000', // Khung bao quanh mỗi đối tượng
                                        padding: '2px 5px',
                                        margin: '5px',
                                        whiteSpace: 'nowrap', // Đảm bảo nội dung không xuống dòng
                                        textAlign: 'center',
                                        alignSelf: 'flex-start'
                                    }}
                                    >
                                        {category.name}
                                    </div>
                                )}
                            </div>



                        </Col>
                    </Row>


                </Container>
            </>}
        </>
    )

}

export default ProductViewForShipper