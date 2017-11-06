
document.getElementById("download").onclick = function() {

        html2canvas(document.getElementById("pdf"), {
            scale: 2,
            // dpi: 144,
            onrendered: function(canvas) {
                // document.body.appendChild(canvas);
                //通过html2canvas将html渲染成canvas，然后获取图片数据
                var imgData = canvas.toDataURL('image/jpeg');

                //初始化pdf，设置相应格式
                var doc = new jsPDF("p", "mm", "a4");

                //这里设置的是a4纸张尺寸
                doc.addImage(imgData, 'JPEG', 0, 0, 210, 297);

                //输出保存命名为content的pdf
                doc.save('content.pdf');
            }
        });

    }
