import React, { useEffect, useRef, useState } from 'react';
import '../static/css/Home.css';
import videoSrc from '../static/image/Giao Hàng Nhanh- Vận Chuyển Siêu Nhanh, Giá Siêu Tốt  GHN.VN Giao Hàng Nhanh.mp4';
import { Container } from 'react-bootstrap';
const images = [
    'https://file.hstatic.net/200000472237/file/1920x700_f4746c6cb4dc4acf9e7adc2e8c720af2.png',
    'https://file.hstatic.net/200000472237/file/img_2144_0655059b42444911af2bb255abc3b4a8.png',
    'https://file.hstatic.net/200000472237/file/ghn-fullfilment_383eda5c46494f2d_676fe52e6c884663aefa95b484b641f6.jpg'
];
const Home = () => {
    const [currentIndex, setCurrentIndex] = useState(0);
    const [transition, setTransition] = useState('fade');
    const videoRef = useRef(null);



    useEffect(() => {
        const interval = setInterval(() => {
            setTransition('fade'); // Thêm lớp chuyển động khi chuyển ảnh
            setCurrentIndex((prevIndex) => (prevIndex + 1) % images.length);
        }, 3000);

        return () => clearInterval(interval);
    }, []);

    useEffect(() => {
        const video = videoRef.current;
        if (video) {
            video.play();
        }
    }, []);

    const handleSelectImage = (index) => {
        setTransition('fade');
        setCurrentIndex(index);
    };
    return (
        <>

            <div >

                <div className="slider" style={{
                    position: 'relative',
                    width: '100%',
                    height: 'auto',
                    overflow: 'hidden'
                }} >

                    <img src={images[currentIndex]} alt="slider" className="slider-image" />

                </div>

                <div className="thumbnail-container">


                    <div key={0} className={`thumbnail ${0 === currentIndex ? 'active' : ''}`}

                        onClick={() => handleSelectImage(0)}
                    >
                        Dịch vụ vận chuyển
                        giao hàng nặng

                    </div>

                    <div key={1} className={`thumbnail ${1 === currentIndex ? 'active' : ''}`}
                        onClick={() => handleSelectImage(1)}
                    >
                        Dịch vụ giao hàng nhanh

                    </div>
                    <div key={2} className={`thumbnail ${2 === currentIndex ? 'active' : ''}`}
                        onClick={() => handleSelectImage(2)}
                        style={{ borderRight: "none" }}
                    >
                        Miễn phí không giới hạn

                    </div>
                </div>
            </div>


            <Container>
                <section >
                    <h2 style={{ flexDirection: 'column' }} class="section-banner ">Giao nhanh hơn 6 tiếng</h2>
                    <div style={{ flexDirection: 'column' }} class="section-banner ">Giúp bạn giao hàng đến người nhận nhanh hơn 6 tiếng so với các đơn vị vận chuyển khác.</div>

                    <video class="section-banner "
                        width="1100"
                        autoPlay
                        loop
                        muted
                        playsInline
                    ><source src={videoSrc} type="video/mp4" />
                        Trình duyệt của bạn không hỗ trợ video tag.
                    </video>

                    <div class="section-banner">
                        <div className='text-center'>
                            <img height='50px' src='https://theme.hstatic.net/200000472237/1001083717/14/icon-app1_small.png?v=604'></img>
                            <div className='mt-2'>Top công ty giao <br />
                                nhận hàng đầu VN</div>
                        </div>
                        <div className='text-center'>
                            <img height='50px' src='https://theme.hstatic.net/200000472237/1001083717/14/icon-app2_small.png?v=604'></img>
                            <div className='mt-2'>Mạng lưới phủ sóng
                                <br /> 100% 63 tỉnh thành
                            </div>
                        </div>
                        <div className='text-center'>
                            <img height='50px' src='https://theme.hstatic.net/200000472237/1001083717/14/icon-app3_small.png?v=604'></img>
                            <div className='mt-2'>Giao hàng nhanh
                                <br /> Tỷ lệ hoàn hàng thấp
                            </div>
                        </div>
                        <div className='text-center'>
                            <img height='50px' src='https://theme.hstatic.net/200000472237/1001083717/14/icon-app4_small.png?v=604'></img>
                            <div className='mt-2'>Hệ thống quản lý
                                <br /> trực tuyến 24/7
                            </div>
                        </div>
                    </div>
                </section>

            </Container>

        </>
    );
}
export default Home;