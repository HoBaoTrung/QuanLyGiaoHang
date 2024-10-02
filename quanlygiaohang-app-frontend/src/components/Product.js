
import { useEffect, useState, useCallback, useRef, useContext } from "react"
import { Button, Col, Container, Form, Image, Row, Spinner } from "react-bootstrap"
import { authApi, endpoint } from "../configs/Apis"
import { useParams, useLocation, Link, useNavigate } from "react-router-dom"
import MySpinner from "../layout/Spinner"
import UploadImage from "../common/UploadImage"
import { MyUserContext } from "../App"

const Product = () => {
    const { productId } = useParams();
    const [user] = useContext(MyUserContext);
    const proofImg = useRef(null);
    const [product, setProduct] = useState(null);
    const [dauGias, setDauGias] = useState(null);
    const [isShipperView] = useState(() => window.location.pathname.includes('viewselectorderforshipper'));
    const [voucher, setVoucher] = useState(null);
    const [selectVoucher, setSelectVoucher] = useState(null);
    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);
    const adminView = queryParams.get('adminview') === 'true';
const nav = useNavigate()

    const handleImageChange = (image) => {
        proofImg.current = image;
    };

    const loadProduct = useCallback(async () => {
        try {
            const res = await authApi().get(endpoint['get-order-detail'](productId));
            setDauGias(res.data.dauGiaSet);
            setProduct(res.data);
            console.info(res.data)
        } catch (ex) { console.error(ex); }
    }, [productId]);

    const loadVoucher = useCallback(async () => {
        try {
            const res = await authApi().get(endpoint['get-owner-voucher']);
            setVoucher(res.data);
        } catch (ex) { console.error(ex); }
    }, []);

    const fetchPaymentResult = useCallback(async () => {
        try {
            const params = new URLSearchParams(location.search);
            const savedDauGiaPay = sessionStorage.getItem('dauGiaPay');
            const body = JSON.parse(savedDauGiaPay);
            const vnpResponseCode = params.get('vnp_ResponseCode');
            sessionStorage.removeItem('dauGiaPay');

            if (vnpResponseCode === '00') {
                await authApi().post(endpoint['pay-return'], {
                    dauGiaSet: body.dauGiaSet,
                    dauGiaSelected: body.dauGiaSelected,
                    voucherId: body.voucherId
                });
            }
        } catch (error) { console.error(error); }
        loadProduct();
    }, [location, loadProduct]);

    useEffect(() => {
        fetchPaymentResult();
        loadProduct();
        if (!isShipperView) loadVoucher();
    }, [fetchPaymentResult, loadProduct, loadVoucher, isShipperView]);

    const formatter = new Intl.NumberFormat('vi-VN', {
        style: 'decimal',
        minimumFractionDigits: 0,
        maximumFractionDigits: 0
    });

    return (
        <>
            <h1 className="text-center text-info">CHI TIẾT SẢN PHẨM</h1>
            {!product ? <Spinner animation="border" /> : (
                <Container>
                    <Row>
                        <Col md={7} xs={6}>
                            <Image src={product.image} style={{ width: '35rem' }} />
                            {dauGias.length < 1 ? <h1 className="text-danger mt-3">Chưa có shipper đấu giá</h1> : <></>}
                            <div style={{ height: '20rem', overflowY: "auto" }}>
                                {dauGias && dauGias.map(d => (
                                    <div className="mt-3" style={{ borderBottom: '1px solid', display: 'flex' }}>
                                        <div className="mr-3">
                                            <h3>
                                                {d.selected && (
                                                    <div>
                                                        <h2 className="text-success">
                                                            {isShipperView ? 'Bạn đã được chọn để giao hàng' : <>Đã có shipper nhận giao
                                                                <br></br>
                                                                <Link to={`/shipperDetail/${d.shipper.id}`}>
                                                                    <Button variant="info" className="mt-4 mb-3">Xem shipper</Button>
                                                                </Link>
                                                            </>}

                                                        </h2>

                                                    </div>
                                                )}
                                                <Image className="mr-2" src={d.shipper.user.avatar} style={{ width: '5rem' }} />
                                                {d.shipper.user.username}<br />
                                                Giá vận chuyển: {formatter.format(d.price)}Đ
                                            </h3>

                                        </div>
                                        {d.selected !== true ?
                                            <div>
                                                <Link to={`/shipperDetail/${d.shipper.id}`}>
                                                    <Button variant="info" style={{ "display": "block" }} className="mt-4 mb-3">Xem shipper</Button>
                                                </Link>
                                                {!adminView &&
                                                    <Button variant="success" style={{ "display": "block" }} type="submit" onClick={async () => {

                                                        const listRefuse = Array.from(dauGias)
                                                            .filter(obj => obj !== d)
                                                            .map(obj => obj);

                                                        let res = await authApi().post(endpoint['pay'],
                                                            {
                                                                dauGiaSelected: d,
                                                                voucherId: selectVoucher
                                                            });
                                                        const updatedDauGiaPay = {
                                                            dauGiaSet: listRefuse,
                                                            dauGiaSelected: d,
                                                            voucherId: selectVoucher
                                                        };
                                                        sessionStorage.setItem('dauGiaPay', JSON.stringify(updatedDauGiaPay));
                                                        window.location.href = (res.data)

                                                    }
                                                    }>Chọn</Button>}

                                            </div> :
                                            <></>}
                                    </div>
                                ))}
                            </div>



                            {
                                product.paymentDate && (
                                    product.proof ? (
                                        <>
                                            <h3 className="text-success mt-3">Hàng đã được giao</h3>
                                            <Image src={product.proof.image} style={{ width: '20rem' }} />
                                        </>
                                    ) : <h3 className="text-danger mt-3">Hàng chưa được giao</h3>
                                )
                            }
                            {isShipperView ? (
                                <div className="mt-3 p-3" style={{ border: '1px solid green' }}>
                                    <h3>Xác minh đã giao hàng</h3>
                                    <UploadImage onImageChange={handleImageChange} />
                                    <Button variant="success" className="mt-3"
                                        onClick={() => {

                                            if (proofImg.current !== null) {
                                                let form = new FormData();
                                                form.append('image', proofImg.current)
                                                let res = authApi().post(endpoint['add-shipper-proof'](productId), form)
                                            }
                                            else { alert("Hãy nhập hình minh chứng") }



                                        }}
                                    >Xác nhận đã giao</Button>
                                </div>
                            ) : null}

                        </Col>
                        <Col md={5} xs={6}>
                            <h2>Thông tin đơn hàng</h2>
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
                            <br />
                            <h2>Thông tin người gửi</h2>
                            <h5>Tên: {product.senderName}</h5>
                            <h5>Địa chỉ: {product.deliveryAddress}</h5>
                            <h5>Số điện thoại: {product.senderPhone}</h5>
                            <br />
                            <h2>Thông tin người nhận</h2>
                            <h5>Tên: {product.receiverName}</h5>
                            <h5>Địa chỉ: {product.receiverAddress}</h5>
                            <h5>Số điện thoại: {product.receiverPhone}</h5>

                            <br />
                            {(!isShipperView && !adminView) && (
                                <>
                                    <h5>Voucher của bạn</h5>
                                    <Form.Select onChange={(e) => setSelectVoucher(e.target.value)}>
                                        <option value="">Chọn voucher</option>
                                        {voucher ? voucher.map(p => (
                                            <option key={p.id} value={p.id}>
                                                Giảm: {p.value + p.measurement}
                                            </option>
                                        )) : <MySpinner />}
                                    </Form.Select>
                                </>
                            )}

                            {(adminView) && (
                                <>
                                {user.data.id===product.user.id ? 
                                    <Button variant='info' className='mr-2' onClick={() => nav(`/myprofile`)}>Xem user</Button>:
                                    <Button variant='info' className='mr-2' onClick={() => nav(`/admin/customersDetail/${product.user.id}`)}>Xem user</Button>
                                }
                                </>
                            )}
                        </Col>
                    </Row>
                </Container>
            )}
        </>
    );
};

export default Product;
