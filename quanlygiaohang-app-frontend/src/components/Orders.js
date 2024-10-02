import { useNavigate, useParams } from "react-router-dom"
import { useEffect, useState } from "react"
import { Button, Card, Col, Container, Row, Spinner } from "react-bootstrap"
import { authApi, endpoint } from "../configs/Apis"
import Filter from "../common/Filter"
import ButtonCheckPay from "../common/ButtonCheckPay"


const Orders = () => {
  const [products, setProduct] = useState(null)
  const [loading, setLoading] = useState(false)
  const [checkPay, setCheckPay] = useState(false)
  const [page, setPage] = useState(1)
  const nav = useNavigate();
  const [filterData, setFilterData] = useState(null);
  const { orderStatus } = useParams()
  const loadMore = () => {
    if (!loading)
      setPage(page + 1)
  }

  const loadProduct = async () => {
    try {
      let form = new FormData()
      if (filterData !== null) {
        for (let key in filterData) {
          form.append(key, filterData[key])
        }
      }

      form.append("isPay", checkPay)
      form.append('page', page)
      form.append('status', orderStatus)

      let url = `${endpoint['get-orders']}`
      const res = await authApi().post(url, form)

      if (page === 1)
        setProduct(res.data)
      else setProduct(current => {
        return [...current, ...res.data]
      })
    }
    catch (ex) { console.log(ex) }
    finally { setLoading(false) }
  }

  const handleFilter = (data) => {
    setPage(1)
    setFilterData(data);
  };

  useEffect(() => {
    loadProduct();
  }, [orderStatus, page, filterData, checkPay]);
  useEffect(() => {
    setPage(1);
  }, [orderStatus]);

  const formatter = new Intl.NumberFormat('vi-VN', {
    style: 'decimal',
    minimumFractionDigits: 0, // Không cần số thập phân
    maximumFractionDigits: 0  // Không cần số thập phân
  });

  if (products === null)
    return <Spinner animation="border" />;

  const renderOwnerButtons = (p) => (
    <>
      <Button className="mr-3" onClick={() => nav(`/product/${p.id}`)} variant="primary">
        Xem chi tiết
      </Button>
      <Button variant="danger">Xóa</Button>
    </>
  );

  const renderNotYetPriced = (p) => (
    <>
      <Card.Text className="text-info">Bạn chưa ra giá cho sản phẩm này</Card.Text>
      <Button className="mr-3" onClick={() => nav(`/viewproductforshipper/${p.id}`)} variant="primary">
        Ra giá
      </Button>
    </>
  );

  const renderPriced = (p) => (
    <>
      <Card.Text>Giá bạn đặt: {formatter.format(p.shipperPayPrice)}đ</Card.Text>
      <Button
        className="mr-3"
        onClick={() => nav(p.isPay ? `/viewselectorderforshipper/${p.id}` : `/viewproductforshipper/${p.id}`)}
        variant="primary"
      >
        {p.isPay ? 'Xem chi tiết' : 'Ra giá'}
      </Button>
    </>
  );

  const renderBasicPrice = (p) => (
    <Card.Text>Giá cơ bản: {formatter.format(p.basicPrice)}đ</Card.Text>
  );

  const renderButtons = (p) => {
    if (orderStatus === "owner") return renderOwnerButtons(p);
    if (orderStatus === "NotYetPriced") return renderNotYetPriced(p);
    return renderPriced(p);
  };

  
  return (
    <>
      <Container fluid>
        {orderStatus === "NotYetPriced" ? <Filter onSearch={handleFilter} /> : <></>}
        {orderStatus === "null" || orderStatus === "owner" ? <ButtonCheckPay onCheck={(value) => {
          setCheckPay(value)
        }} /> : <></>}

        <Row className="w-100">
          {products.map(p => (
            <Col key={p.id} md={3} xs={12} className="mb-3">
              <Card style={{ width: '18rem' }}>
                <Card.Img variant="top" style={{ width: '100%' }} src={p.image} />
                <Card.Body>
                  <Card.Title>{p.name}</Card.Title>
                  <Card.Text>Số lượng: {p.quantity}</Card.Text>
                  <Card.Text>Giao đến: {p.toDistrictId.districtName}, {p.toDistrictId.provinceId.provinceName}</Card.Text>
                  <Card.Text>
                    Trạng thái:
                    <span className={`text ${p.isPay ? "text-success" : "text-danger"}`}>
                      {p.isPay ? "Đã thanh toán" : "Chưa thanh toán"}
                    </span>
                  </Card.Text>
                  <>
                    {orderStatus !== "owner" ? renderBasicPrice(p) : <></>}
                    {renderButtons(p)}
                  </>
                </Card.Body>
              </Card>
            </Col>
          ))}
        </Row>

      </Container>

      <div className="text-center">
        <Button onClick={loadMore} disabled={loading}>Xem thêm</Button>
      </div>
    </>
  );
}

export default Orders;