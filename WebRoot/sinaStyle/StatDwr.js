
function StatDwr() { }
StatDwr._path = '/dwr';

StatDwr.getStatList = function(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, callback) {
    DWREngine._execute(StatDwr._path, 'StatDwr', 'getStatList', p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, callback);
}
