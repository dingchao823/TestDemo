package com.example.base.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 商品信息
 *
 * @author yjy
 * @created 2016年03月26日
 */
@SuppressWarnings("serial")
public class Goods{

    public final static int TYPE_NULL = -1;
    public final static int TYPE_UserCarFragment = 100;//购物车操作


    public final static int ACTION_NULL = -1;
    public final static int ACTION_CHECK_CHANGE = 100;//勾选状态改变
    public final static int ACTION_ADD = 101;//数量加1
    public final static int ACTION_DEL = 102;//数量减1
    public final static int ACTION_DELETE = 103;//删除商品
    public final static int ACTION_SELECT_PROMOTION = 104;//选择促销活动

    private int eventType = TYPE_NULL;
    private int eventAction = -1;
    private String SellPoint;

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public int getEventAction() {
        return eventAction;
    }

    public void setEventAction(int eventAction) {
        this.eventAction = eventAction;
    }


    private boolean isSubscrible;
    private boolean JoinedProduct;
    private String SubTitle;
    private boolean SoldOut;

    public String getSubTitle() {
        return SubTitle;
    }

    public void setSubTitle(String subTitle) {
        SubTitle = subTitle;
    }

    public boolean isJoinedProduct() {
        return JoinedProduct;
    }

    public void setJoinedProduct(boolean joinedProduct) {
        JoinedProduct = joinedProduct;
    }

    public boolean isSubscrible() {
        return isSubscrible;
    }

    public void setSubscrible(boolean subscrible) {
        isSubscrible = subscrible;
    }
    private boolean isLoad=false;

    public boolean isLoad() {
        return isLoad;
    }

    public void setLoad(boolean load) {
        isLoad = load;
    }

    public String getSellPoint() {
        return SellPoint;
    }

    public void setSellPoint(String sellPoint) {
        SellPoint = sellPoint;
    }

    private String VideoUrl;//视频地址
    /***/
    private int Id;

    /**
     * 用户收藏时：收藏时间
     */
    private String CreateTime;

    private List<Goods> ChildProducts;

    public List<Goods> getChildProducts() {
        return ChildProducts;
    }

    public void setChildProducts(List<Goods> childProducts) {
        ChildProducts = childProducts;
    }

    private String CouponText;

    public String getCouponText() {
        return CouponText;
    }

    public void setCouponText(String couponText) {
        CouponText = couponText;
    }

    private String ActivityLabel;
    /**
     * 商品id
     */
    private int ProductId;

    /**
     * 商品变体Id
     */
    private int ProductVariantId;

    /**
     * 商品名
     */
    private String ProductName;

    /**
     * 是否标品
     */
    private boolean Standard;

    /**
     * 商品单位 专指标品一份的价格单位
     */
    private String Unit;

    /**
     * 商品预估重量 非标品一份（UnitPeriodMoney）的价格单位
     */
    private double Weight;

    /**
     * 商品规格
     */
    private String PvStandard;

    /**
     * 商品图片Id
     */
    private int PictureId;

    public String getPictureIds() {
        return PictureIds;
    }

    public void setPictureIds(String pictureIds) {
        PictureIds = pictureIds;
    }

    /**
     * 商品图片Id(首页推荐商品楼层)
     */
    private String PictureIds;

    /**
     * 每份价格 （标品时与PeriodMoney一样）
     */
    private double UnitPeriodMoney;

    /**
     * 单位价格 PriceName（食行价） （老版本对应字段：sx_price）
     */
    private double PeriodMoney;

    /**
     * 原价 OldPriceName（市场价） old_price 价格单位--》 标品：Unit 非标品：500g
     */
    private double DefaultMoney;

    /**
     * 价格名（食行价） （老版本对应字段：sx_price_name）
     */
    private String PriceName;

    /**
     * 1:新品 2:促销 3:人气 4:直降 5:惠民
     */
    private int IconStatus;

    /**
     * 是否为冻品
     */
    private boolean IsFreeze;

    /**
     * 商品库存
     */
    private int StockQty = 99;

    /**
     * 商品编号
     */
    private String Number;

    /**
     * 加入购物车提示信息
     */
    private String AddCartTip;

    /**
     * 订单中商品结算价格
     */
    private int Amount;

    /**
     * 订单中商品是否已经结算
     */
    private boolean IsAccountSettle;

    /**
     * 估算重量
     */
    private double EstimatingWeight;

    /**
     * 实际称重
     */
    private double WeighingWeight;

    /**
     * 商品冻结金额
     */
    private double FreezeMoney;

    /**
     * 商品结算金额
     */
    private double SettleMoney;

    /**
     * 购买商品数量
     */
    private int Quantity;

    /**
     * 变体状态id
     */
    private int ProductVariantStatus;

    /**
     * 变体状态名
     */
    private String ProductVariantStatusName;

    /**
     * 缺货补偿信息展示
     */
    private String OutOfStockCompensateTip;

    public List<String> getOutOfStockCompensateTips() {
        return OutOfStockCompensateTips;
    }

    /**
     * 组合装缺货补偿信息展示
     */
    private List<String> OutOfStockCompensateTips;

    /***/
    private int PType;

    /***/
    private String PTypeName;

    /**
     * 订单编号
     */
    private String OrderNumber;

    /**
     * 订单id
     */
    private int OrderId;

    /**
     * 此商品是否选中 等状态标识
     */
    private boolean Checked;

    /**
     * 用户待评价商品结算金额
     */
    private double Price;

    /**
     * 用户已评价商品 评价审核结果状态
     */
    private int Status;

    /**
     * 用户已评价商品 评价审核结果状态语
     */
    private String Tip;

    /**
     * 用户已评价商品 评价审核后获得的积分
     */
    private int GetPoint;

    /**
     * 退化货申请变体id
     */
    private int SosPvId;

    /**
     * 退换货申请类型 1 ：退货 2：换货
     */
    private int ApplyType;

    /**
     * 退换货申请类型名称
     */
    private String ApplyTypeName;

    /**
     * 退换货申请结果状态名称
     */
    private String StatusName;

    /**
     * 进口
     */
    private boolean IsImport;

    // /////////////购物车需要的信息/////////////////////////////

    /**
     * 购车中描述信息
     */
    private String Description;

    /**
     * 可以购买的数量
     */
    private int CanBuyAmount;

    /**
     * 是否可以购买
     */
    private boolean CanBuy;

    /**
     * 购车中提示信息
     */
    private String Tips;

    /**
     * 购物车中此商品小计
     */
    private double PriceSmallTotal;

    /**
     * 商品变体类型
     */
    private int ProductVariantType;

    /**
     * 商品变体类型名称
     */
    private String ProductVariantTypeName;

    /**
     * 购物车中此商品在 编辑状态下是否选中
     */
    private boolean CheckedOnEdit = false;

    private boolean ShowCovetShade;

    /**
     * 促销是价格
     */
    private double DiscountPrice;

    /**
     * 商品溯源
     */
    private int SospvId;

    /**
     * 礼包商品
     */
    private boolean gift = false;


    /**
     * 是否上架
     */
    private boolean Published = true;
    /**
     * Max会员
     */
    private double MaxUnitPeriodMoney;
    private boolean MaxMark;
    private double UnitPrice;
    private double MaxPeriodMoney;

    public double getMaxPeriodMoney() {
        return MaxPeriodMoney;
    }

    public void setMaxPeriodMoney(double maxPeriodMoney) {
        MaxPeriodMoney = maxPeriodMoney;
    }

    /*
     *首页可配置楼层
     */
    private String SellingPointsOne;
    private String SellingPointsTwo;
    private String PictureLocation;


    private String Tag;
    private String CopyWriter;

    /**
     * SPU
     *
     * @return
     */
    private String Specification;
    private boolean spuSelected = false;
    private int spuCount = 1;
    private int spuId;

    String PromotionText;


    int Index=0;
    int ThirdCategory=0;
    String ThirdCategoryName;

    public String getThirdCategoryName() {
        return ThirdCategoryName;
    }

    public void setThirdCategoryName(String thirdCategoryName) {
        ThirdCategoryName = thirdCategoryName;
    }

    public int getThirdCategory() {
        return ThirdCategory;
    }

    public void setThirdCategory(int thirdCategory) {
        ThirdCategory = thirdCategory;
    }

    public int getIndex() {
        return Index;
    }

    public void setIndex(int index) {
        Index = index;
    }

    public String getActivityLabel() {
        return ActivityLabel;
    }

    public void setActivityLabel(String activityLabel) {
        ActivityLabel = activityLabel;
    }

    public String getPromotionText() {
        return PromotionText;
    }

    public void setPromotionText(String promotionText) {
        PromotionText = promotionText;
    }

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }

    private boolean isFav;
    private int position;

    public String getZgEventPosition() {
        return zgEventPosition;
    }

    public void setZgEventPosition(String zgEventPosition) {
        this.zgEventPosition = zgEventPosition;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private String zgEventPosition;


    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }

    public String getVideoUrl() {
        return VideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        VideoUrl = videoUrl;
    }

    public String getCopyWriter() {
        return CopyWriter;
    }

    public void setCopyWriter(String copyWriter) {
        CopyWriter = copyWriter;
    }

    public int getSpuId() {
        return spuId;
    }

    public void setSpuId(int spuId) {
        this.spuId = spuId;
    }

    public double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        UnitPrice = unitPrice;
    }

    public int getSpuCount() {
        return spuCount;
    }

    public void setSpuCount(int spuCount) {
        this.spuCount = spuCount;
    }

    public boolean isSpuSelected() {
        return spuSelected;
    }

    public void setSpuSelected(boolean spuSelected) {
        this.spuSelected = spuSelected;
    }


    public String getPictureLocation() {
        return PictureLocation;
    }


    public String getSellingPointFirst() {
        return SellingPointsOne;
    }

    public void setSellingPointFirst(String sellingPointFirst) {
        SellingPointsOne = sellingPointFirst;
    }

    public String getSellingPointSecond() {
        return SellingPointsTwo;
    }

    public void setSellingPointSecond(String sellingPointSecond) {
        SellingPointsTwo = sellingPointSecond;
    }


    public void setPictureLocation(String pictureLocation) {
        PictureLocation = pictureLocation;
    }

    public int getStockQty() {
        return StockQty;
    }

    public void setStockQty(int stockQty) {
        this.StockQty = stockQty;
    }

    public boolean isPublished() {
        return Published;
    }

    public void setPublished(boolean published) {
        this.Published = published;
    }

    public String getSpecification() {
        return Specification;
    }

    public void setSpecification(String specification) {
        this.Specification = specification;
    }

    public double getMaxUnitPeriodMoney() {
        return MaxUnitPeriodMoney;
    }

    public void setMaxUnitPeriodMoney(double maxUnitPeriodMoney) {
        MaxUnitPeriodMoney = maxUnitPeriodMoney;
    }

    public boolean isMaxMark() {
        return MaxMark;
    }

    public void setMaxMark(boolean maxMark) {
        MaxMark = maxMark;
    }


    // /////////////////

    public boolean isGift() {
        return gift;
    }

    public void setGift(boolean gift) {
        this.gift = gift;
    }

    public int getSonOrderProductVariantId() {
        return SospvId;
    }

    public void setSonOrderProductVariantId(int sonOrderProductVariantId) {
        SospvId = sonOrderProductVariantId;
    }

    public double getDiscountPrice() {
        return DiscountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        DiscountPrice = discountPrice;
    }

    private String image;

    public boolean isShowCovetShade() {
        return ShowCovetShade;
    }

    public void setShowCovetShade(boolean showCovetShade) {
        ShowCovetShade = showCovetShade;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isCheckedOnEdit() {
        return CheckedOnEdit;
    }

    public void setCheckedOnEdit(boolean checkedOnEdit) {
        CheckedOnEdit = checkedOnEdit;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getCanBuyAmount() {
        return CanBuyAmount;
    }

    public void setCanBuyAmount(int canBuyAmount) {
        CanBuyAmount = canBuyAmount;
    }

    public boolean isCanBuy() {
        return CanBuy;
    }

    public void setCanBuy(boolean canBuy) {
        CanBuy = canBuy;
    }

    public String getTips() {
        return Tips;
    }

    public void setTips(String tips) {
        Tips = tips;
    }

    public double getPriceSmallTotal() {
        return PriceSmallTotal;
    }

    public void setPriceSmallTotal(double priceSmallTotal) {
        PriceSmallTotal = priceSmallTotal;
    }

    public int getProductVariantType() {
        return ProductVariantType;
    }

    public void setProductVariantType(int productVariantType) {
        ProductVariantType = productVariantType;
    }

    public String getProductVariantTypeName() {
        return ProductVariantTypeName;
    }

    public void setProductVariantTypeName(String productVariantTypeName) {
        ProductVariantTypeName = productVariantTypeName;
    }

    public int getApplyType() {
        return ApplyType;
    }

    public void setApplyType(int applyType) {
        ApplyType = applyType;
    }

    public String getApplyTypeName() {
        return ApplyTypeName;
    }

    public void setApplyTypeName(String applyTypeName) {
        ApplyTypeName = applyTypeName;
    }

    public String getStatusName() {
        return StatusName;
    }

    public void setStatusName(String statusName) {
        StatusName = statusName;
    }

    public int getSosPvId() {
        return SosPvId;
    }

    public void setSosPvId(int sosPvId) {
        SosPvId = sosPvId;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getTip() {
        return Tip;
    }

    public void setTip(String tip) {
        Tip = tip;
    }

    public int getGetPoint() {
        return GetPoint;
    }

    public void setGetPoint(int getPoint) {
        GetPoint = getPoint;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public boolean isChecked() {
        return Checked;
    }

    public void setChecked(boolean checked) {
        Checked = checked;
    }

    public String getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(String OrderNumber) {
        this.OrderNumber = OrderNumber;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int OrderId) {
        this.OrderId = OrderId;
    }

    public double getEstimatingWeight() {
        return EstimatingWeight;
    }

    public void setEstimatingWeight(double estimatingWeight) {
        EstimatingWeight = estimatingWeight;
    }

    public double getWeighingWeight() {
        return WeighingWeight;
    }

    public void setWeighingWeight(double weighingWeight) {
        WeighingWeight = weighingWeight;
    }

    public double getFreezeMoney() {
        return FreezeMoney;
    }

    public void setFreezeMoney(double freezeMoney) {
        FreezeMoney = freezeMoney;
    }

    public double getSettleMoney() {
        return SettleMoney;
    }

    public void setSettleMoney(double settleMoney) {
        SettleMoney = settleMoney;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getProductVariantStatus() {
        return ProductVariantStatus;
    }

    public void setProductVariantStatus(int productVariantStatus) {
        ProductVariantStatus = productVariantStatus;
    }

    public String getProductVariantStatusName() {
        return ProductVariantStatusName;
    }

    public void setProductVariantStatusName(String productVariantStatusName) {
        ProductVariantStatusName = productVariantStatusName;
    }

    public String getOutOfStockCompensateTip() {
        return OutOfStockCompensateTip;
    }

    public void setOutOfStockCompensateTip(String outOfStockCompensateTip) {
        OutOfStockCompensateTip = outOfStockCompensateTip;
    }


    public int getPType() {
        return PType;
    }

    public void setPType(int pType) {
        PType = pType;
    }

    public String getPTypeName() {
        return PTypeName;
    }

    public void setPTypeName(String pTypeName) {
        PTypeName = pTypeName;
    }

    public boolean isIsAccountSettle() {
        return IsAccountSettle;
    }

    public void setIsAccountSettle(boolean isAccountSettle) {
        IsAccountSettle = isAccountSettle;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public String getAddCartTip() {
        return AddCartTip;
    }

    public void setAddCartTip(String addCartTip) {
        this.AddCartTip = addCartTip;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        this.Number = number;
    }

    public int getIconStatus() {
        return IconStatus;
    }

    public void setIconStatus(int iconStatus) {
        IconStatus = iconStatus;
    }

    public boolean isIsFreeze() {
        return IsFreeze;
    }

    public void setIsFreeze(boolean isFreeze) {
        IsFreeze = isFreeze;
    }


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public int getProductVariantId() {
        return ProductVariantId;
    }

    public void setProductVariantId(int productVariantId) {
        ProductVariantId = productVariantId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public boolean isStandard() {
        return Standard;
    }

    public void setStandard(boolean standard) {
        Standard = standard;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public double getWeight() {
        return Weight;
    }

    public void setWeight(double weight) {
        Weight = weight;
    }

    public String getPvStandard() {
        return PvStandard;
    }

    public void setPvStandard(String pvStandard) {
        PvStandard = pvStandard;
    }

    public int getPictureId() {
        return PictureId;
    }

    public void setPictureId(int pictureId) {
        PictureId = pictureId;
    }

    public double getUnitPeriodMoney() {
        return UnitPeriodMoney;
    }

    public void setUnitPeriodMoney(double unitPeriodMoney) {
        UnitPeriodMoney = unitPeriodMoney;
    }

    public double getPeriodMoney() {
        return PeriodMoney;
    }

    public void setPeriodMoney(double periodMoney) {
        PeriodMoney = periodMoney;
    }

    public double getDefaultMoney() {
        return DefaultMoney;
    }

    public void setDefaultMoney(double defaultMoney) {
        DefaultMoney = defaultMoney;
    }

    public String getPriceName() {
        return PriceName;
    }

    public void setPriceName(String priceName) {
        PriceName = priceName;
    }

    public boolean isIsImport() {
        return IsImport;
    }

    public boolean isSoldOut() {
        return SoldOut;
    }

    public void setSoldOut(boolean soldOut) {
        SoldOut = soldOut;
    }

    public void setIsImport(boolean isImport) {
        IsImport = isImport;
    }

    public String getColor() {
        String Str = "";
        switch (PType) {
            case 8:
                Str = "#FF9900";
                break;
            case 3:
                Str = "#00A653";
                break;
            case 7:
                Str = "#CC66CC";
                break;
            case 9:
                Str = "#FF6699";
                break;
            default:
                Str = "#FF9900";
                break;
        }
        return Str;
    }

    private Goods(Parcel in) {
        ProductId = in.readInt();
        PictureId = in.readInt();
        ProductName = in.readString();
        ProductVariantId = in.readInt();
        SosPvId = in.readInt();
        Tips = in.readString();
        OrderId = in.readInt();
        Price = in.readDouble();
    }

    public Goods() {
    }

    public static final Parcelable.Creator<Goods> CREATOR = new Parcelable.Creator<Goods>() {
        public Goods createFromParcel(Parcel in) {
            return new Goods(in);
        }

        public Goods[] newArray(int size) {
            return new Goods[size];
        }
    };

    public static Parcelable.Creator<Goods> getCreator() {
        return CREATOR;
    }

    private String markCode;

    public String getMarkCode() {
        return markCode;
    }

    public void setMarkCode(String markCode) {
        this.markCode = markCode;
    }
}
