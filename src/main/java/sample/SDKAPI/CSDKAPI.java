package sample.SDKAPI;

import com.sun.jna.*;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.ptr.*;
import com.sun.jna.win32.StdCallLibrary;

import java.util.ArrayList;

/**
 * 
 * Dll接口
 * 
 * @author Sylar
 * 
 */
public interface CSDKAPI extends StdCallLibrary
{

    public static class ERRORCODE
	{

        public static final int CAP_EC_SUCCESS = 0;
        //初始化视频采集卡失败
        public static final int CAP_EC_INIT_DEVICE_FAILED = -1;
        public static final int CAP_EC_DEVICE_IN_USE = -2;
        public static final int CAP_EC_NOT_SUPPORTED = -3;
        //无效的参数
        public static final int CAP_EC_INVALID_PARAM = -4;
        public static final int CAP_EC_TIMEOUT = -5;
        public static final int CAP_EC_NOT_ENOUGH_MEMORY = -6;
        public static final int CAP_EC_UNKNOWN_ERROR = -7;
        public static final int CAP_EC_ERROR_STATE = -8;
        public static final int CAP_EC_HDCP_PROTECTED_CONTENT = -9;
    }
	public static class CAPTURETYPE
	{

        public static final int CAPTURETYPE_SD = 1;
        public static final int CAPTURETYPE_HD = 2;
        public static final int CAPTURETYPE_ALL = 3;
    }
	public static class VIDEOSOURCE
	{

        public static final int VIDEOSOURCE_COMPOSITE = 0;
        public static final int VIDEOSOURCE_SVIDEO = 1;
        public static final int VIDEOSOURCE_COMPONENT = 2;
        public static final int VIDEOSOURCE_HDMI = 3;
        public static final int VIDEOSOURCE_VGA = 4;
        public static final int VIDEOSOURCE_SDI = 5;
        public static final int VIDEOSOURCE_ASI = 6;
        public static final int VIDEOSOURCE_DVI = 7;
    }
	public static class AUDIOSOURCE
	{

        public static final int AUDIOSOURCE_NONE = 0x00000000;
        public static final int AUDIOSOURCE_COMPOSITE = 0x00000001;
        public static final int AUDIOSOURCE_SVIDEO = 0x00000002;
        public static final int AUDIOSOURCE_COMPONENT = 0x00000004;
        public static final int AUDIOSOURCE_HDMI = 0x00000008;
        public static final int AUDIOSOURCE_VGA = 0x00000010;
        public static final int AUDIOSOURCE_SDI = 0x00000020;
        public static final int AUDIOSOURCE_ASI = 0x00000040;
        public static final int AUDIOSOURCE_DVI = 0x00000080;
        public static final int AUDIOSOURCE_DEFAULT = 0xFFFFFFFF;
    }
	public static class VIDEOFORMAT
	{

        public static final int VIDEOFORMAT_NTSC = 0;
        public static final int VIDEOFORMAT_PAL = 1;
    }
	public static class VIDEORESOLUTION
	{

        public static final int VIDEORESOLUTION_640X480 = 0;
        public static final int VIDEORESOLUTION_704X576 = 1;
        public static final int VIDEORESOLUTION_720X480 = 2;
        public static final int VIDEORESOLUTION_720X576 = 4;
        public static final int VIDEORESOLUTION_1920X1080 = 7;
        public static final int VIDEORESOLUTION_160X120 = 20;
        public static final int VIDEORESOLUTION_176X144 = 21;
        public static final int VIDEORESOLUTION_240X176 = 22;
        public static final int VIDEORESOLUTION_240X180 = 23;
        public static final int VIDEORESOLUTION_320X240 = 24;
        public static final int VIDEORESOLUTION_352X240 = 25;
        public static final int VIDEORESOLUTION_352X288 = 26;
        public static final int VIDEORESOLUTION_640X240 = 27;
        public static final int VIDEORESOLUTION_640X288 = 28;
        public static final int VIDEORESOLUTION_720X240 = 29;
        public static final int VIDEORESOLUTION_720X288 = 30;
        public static final int VIDEORESOLUTION_80X60 = 31;
        public static final int VIDEORESOLUTION_88X72 = 32;
        public static final int VIDEORESOLUTION_128X96 = 33;
        public static final int VIDEORESOLUTION_640X576 = 34;
        public static final int VIDEORESOLUTION_180X120 = 37;
        public static final int VIDEORESOLUTION_180X144 = 38;
        public static final int VIDEORESOLUTION_360X240 = 39;
        public static final int VIDEORESOLUTION_360X288 = 40;
        public static final int VIDEORESOLUTION_768X576 = 41;
        public static final int VIDEORESOLUTION_384x288 = 42;
        public static final int VIDEORESOLUTION_192x144 = 43;
        public static final int VIDEORESOLUTION_1280X720 = 44;
        public static final int VIDEORESOLUTION_1024X768 = 45;
        public static final int VIDEORESOLUTION_1280X800 = 46;
        public static final int VIDEORESOLUTION_1280X1024 = 47;
        public static final int VIDEORESOLUTION_1440X900 = 48;
        public static final int VIDEORESOLUTION_1600X1200 = 49;
        public static final int VIDEORESOLUTION_1680X1050 = 50;
        public static final int VIDEORESOLUTION_800X600 = 51;
        public static final int VIDEORESOLUTION_1280X768 = 52;
        public static final int VIDEORESOLUTION_1360X768 = 53;
        public static final int VIDEORESOLUTION_1152X864 = 54;
        public static final int VIDEORESOLUTION_1280X960 = 55;
        public static final int VIDEORESOLUTION_702X576 = 56;
        public static final int VIDEORESOLUTION_720X400 = 57;
        public static final int VIDEORESOLUTION_1152X900 = 58;
        public static final int VIDEORESOLUTION_1360X1024 = 59;
        public static final int VIDEORESOLUTION_1366X768 = 60;
        public static final int VIDEORESOLUTION_1400X1050 = 61;
        public static final int VIDEORESOLUTION_1440X480 = 62;
        public static final int VIDEORESOLUTION_1440X576 = 63;
        public static final int VIDEORESOLUTION_1600X900 = 64;
        public static final int VIDEORESOLUTION_1920X1200 = 65;
        public static final int VIDEORESOLUTION_1440X1080 = 66;
        public static final int VIDEORESOLUTION_1600X1024 = 67;
        public static final int VIDEORESOLUTION_3840X2160 = 68;
    }
	public static class EVENT
	{

        public static final int EVENT_CAPTUREIMAGE = 1;
        public static final int EVENT_CHECKCOPP = 2;
    }
	public static class COPP_ERR
	{

        public static final int COPP_ERR_UNKNOWN = 0x80000001;
        public static final int COPP_ERR_NO_COPP_HW = 0x80000002;
        public static final int COPP_ERR_NO_MONITORS_CORRESPOND_TO_DISPLAY_DEVICE = 0x80000003;
        public static final int COPP_ERR_CERTIFICATE_CHAIN_FAILED = 0x80000004;
        public static final int COPP_ERR_STATUS_LINK_LOST = 0x80000005;
        public static final int COPP_ERR_NO_HDCP_PROTECTION_TYPE = 0x80000006;
        public static final int COPP_ERR_HDCP_REPEATER = 0x80000007;
        public static final int COPP_ERR_HDCP_PROTECTED_CONTENT = 0x80000008;
        public static final int COPP_ERR_GET_CRL_FAILED = 0x80000009;
    }
	public static class VIDEORENDERER
	{

        public static final int VIDEORENDERER_DEFAULT = 0;
        public static final int VIDEORENDERER_VMR7 = 1;
        public static final int VIDEORENDERER_VMR9 = 2;
        public static final int VIDEORENDERER_EVR = 3;//vista, win7, server 2008
    };
	// video save type
	public static class VIDEOSAVETYPE
	{

        public static final int ST_BMP = 0x00000000;
        public static final int ST_JPG = 0x00000001;
        public static final int ST_AVI = 0x00000002;
        public static final int ST_CALLBACK = 0x00000003;
        public static final int ST_WAV = 0x00000004;
        public static final int ST_PNG = 0x00000006;
        public static final int ST_MPG = 0x00000007;
        public static final int ST_MP4 = 0x00000008;
        public static final int ST_TIFF = 0x00000009;
        public static final int ST_CALLBACK_RGB24 = 0x00000010;
        public static final int ST_CALLBACK_ARGB = 0x00000011;
        public static final int ST_TS = 0x00000013;
    }

	// image type
	public static class IMAGESAVETYPE
	{

        public static final int IMAGETYPE_BMP = 0x00000001;
        public static final int IMAGETYPE_JPG = 0x00000002;
        public static final int IMAGETYPE_PNG = 0x00000003;
        public static final int IMAGETYPE_TIFF = 0x00000004;
    }
    // video capture duration settings
	public static class DURATIONMODE
	{
        public static final int DURATION_TIME = 0x00000001;
        public static final int DURATION_COUNT = 0x00000002;
    }
	public static class RECT extends Structure
	{

        public int left;
        public int top;
        public int right;
        public int bottom;
        public static class ByReference extends RECT implements Structure.ByReference
		{
        }
		public static class ByValue extends RECT implements Structure.ByValue
		{

        }
		@Override
		protected ArrayList<String> getFieldOrder()
		{
			ArrayList<String> a = new ArrayList<String>();
			a.add("left");
			a.add("top");
			a.add("right");
			a.add("bottom");
			return a;
		}

    }
	public static class RESOLUTION_RANGE_INFO extends Structure
	{

        public int iVersion;
        public int iIsRange;
        public int iWidthMin;
        public int iWidthMax;
        public int iHeightMin;
        public int iHeightMax;
		public static class ByReference extends RESOLUTION_RANGE_INFO implements Structure.ByReference
		{

        }
		public static class ByValue extends RESOLUTION_RANGE_INFO implements Structure.ByValue
		{

        }
		@Override
		protected ArrayList<String> getFieldOrder()
		{
			ArrayList<String> a = new ArrayList<String>();
			a.add("iVersion");
			a.add("iIsRange");
			a.add("iWidthMin");
			a.add("iWidthMax");
			a.add("iHeightMin");
			a.add("iHeightMax");
			return a;
		}

    }
	public static class VIDEO_RESOLUTION extends Structure
	{

        public int iVersion;// must set to 1
        public int iVideoResolution;
        public int iIsCustom;
        public int iWidth;
        public int iHeight;
		public static class ByReference extends VIDEO_RESOLUTION implements Structure.ByReference
		{

        }
		public static class ByValue extends VIDEO_RESOLUTION implements Structure.ByValue
		{

        }
		@Override
		protected ArrayList<String> getFieldOrder()
		{
			ArrayList<String> a = new ArrayList<String>();
			a.add("iVersion");
			a.add("iVideoResolution");
			a.add("iIsCustom");
			a.add("iWidth");
			a.add("iHeight");
			return a;
		}

    }
	public static class FRAMERATE_RANGE_INFO extends Structure
	{

        public int iVersion;
        public int iIsRange;
        public int iFramerateMin;
        public int iFramerateMax;
		public static class ByReference extends FRAMERATE_RANGE_INFO implements Structure.ByReference
		{

        }
		public static class ByValue extends FRAMERATE_RANGE_INFO implements Structure.ByValue
		{

        }
		@Override
		protected ArrayList<String> getFieldOrder()
		{
			ArrayList<String> a = new ArrayList<String>();
			a.add("iVersion");
			a.add("iIsRange");
			a.add("iFramerateMin");
			a.add("iFramerateMax");
			return a;
		}

    }
	// capture single image
	public static class CAPTURE_SINGLE_IMAGE_INFO extends Structure
	{

        public int iVersion;
        public int iImageType;
        public int iOverlayMix;
        public RECT rcCapRect;
        public Pointer strFileName;

		public static class ByReference extends CAPTURE_SINGLE_IMAGE_INFO implements Structure.ByReference
		{

        }
		public static class ByValue extends CAPTURE_SINGLE_IMAGE_INFO implements Structure.ByValue
		{

        }
		@Override
		protected ArrayList<String> getFieldOrder()
		{
			ArrayList<String> a = new ArrayList<String>();
			a.add("iVersion");
			a.add("iImageType");
			a.add("iOverlayMix");
			a.add("rcCapRect");
			a.add("strFileName");
			return a;
		}

    }
	public static class VIDEO_SAMPLE_INFO extends Structure
	{

        public int iWidth;
        public int iHeight;
        public int iStride;
        public int iPixelFormat;
		public static class ByReference extends VIDEO_SAMPLE_INFO implements Structure.ByReference
		{

        }
		public static class ByValue extends VIDEO_SAMPLE_INFO implements Structure.ByValue
		{

        }
		@Override
		protected ArrayList<String> getFieldOrder()
		{
			ArrayList<String> a = new ArrayList<String>();
			a.add("iWidth");
			a.add("iHeight");
			a.add("iStride");
			a.add("iPixelFormat");
			return a;
		}

    }
	public static interface NOTIFYEVENTCALLBACK extends Callback
	{

        int invoke(int dwEventCode, Pointer lpEventData, Pointer lpUserData);
    }
	public static interface VIDEOCAPTURECALLBACK extends Callback
	{

        int invoke(VIDEO_SAMPLE_INFO.ByValue VideoInfo, Pointer pbData, int iLength, int iRefTime, Pointer lpEventData, Pointer lpUserData);
    }


	// video capture info
	public static class VIDEO_CAPTURE_INFO extends Structure
	{

        public int iCaptureType;
        public int iSaveType;
        public int iOverlayMix;
        public int iDurationMode;
        public int iDuration;
        public Pointer strFileName;
        public VIDEOCAPTURECALLBACK pCallback;
        public IntByReference lCallbackUserData;
		public static class ByReference extends VIDEO_CAPTURE_INFO implements Structure.ByReference
		{

        }
		public static class ByValue extends VIDEO_CAPTURE_INFO implements Structure.ByValue
		{

        }
		@Override
		protected ArrayList<String> getFieldOrder()
		{
			ArrayList<String> a = new ArrayList<String>();
			a.add("iCaptureType");
			a.add("iSaveType");
			a.add("iOverlayMix");
			a.add("iDurationMode");
			a.add("iDuration");
			a.add("strFileName");
			a.add("pCallback");
			a.add("lCallbackUserData");
			return a;
		}

    }



	CSDKAPI INSTANCE = (CSDKAPI) Native.loadLibrary(".\\AVerCapAPI", CSDKAPI.class);

	/**
	 * 该函数用于返回采集卡数目。
	 *
	 * @param ibrDeviceNum
	 *            输出采集卡数目。
	 * @return
	 */
	public NativeLong AVerGetDeviceNum(IntByReference ibrDeviceNum);

	//>>
    public NativeLong AVerInitialize();
    public NativeLong AVerUninitialize();
    //<<

	/**
	 * 该函数用于返回采集卡类型。
	 *
	 * @param iDeviceIndex
	 *            输入采集卡序号。
	 *
	 * @param ibrDeviceType
	 *            输出采集卡类型。
	 * @return
	 */
	public NativeLong AVerGetDeviceType(int iDeviceIndex, IntByReference ibrDeviceType);

	/**
	 * 该函数用于返回采集卡名称。
	 *
	 * @param iDeviceIndex
	 *            输入采集卡序号。
	 *
	 * @param pDeviceName
	 *            输出采集卡名称的内存，需要自己读取。
	 * @return
	 */
	public NativeLong AVerGetDeviceName(int iDeviceIndex, Pointer pDeviceName);

	/**
	 * 该函数用于返回采集卡序列号。
	 *
	 * @param iDeviceIndex
	 *            输入采集卡序号。
	 *
	 * @param pSerialNum
	 *            输出采集卡序列号的内存，需要自己读取。
	 * @return
	 */
	public NativeLong AVerGetDeviceSerialNum(int iDeviceIndex, Pointer pSerialNum);

	/**
	 * 该函数用于创建采集卡操作句柄。
	 *
	 * @param iDeviceIndex
	 *            输入采集卡序号。
	 *
	 * @param iType
	 *            输入创建采集类型。
	 *
	 * @param hWnd
	 *            输入显示视频窗口句柄。
	 *
	 * @param pbrCaptureObject
	 *            输出已经创建采集卡的句柄。
	 * @return
	 */
	public NativeLong AVerCreateCaptureObjectEx(int iDeviceIndex, int iType, HWND hWnd,
                                                PointerByReference pbrCaptureObject);

	/**
	 * 该函数用于删除采集卡操作句柄。
	 *
	 * @param pCaptureObject
	 *            输入采集卡句柄。
	 * @return
	 */
	public NativeLong AVerDeleteCaptureObject(Pointer pCaptureObject);

	/**
	 * 该函数用于枚举采集卡支持的VideoSource。
	 *
	 * @param pCaptureObject
	 *            输入采集卡句柄。
	 *
	 * @param pSupported
	 *            输出采集卡支持的VideoSource列表。
	 *
	 * @param ibrNum
	 *            输出采集卡支持的VideoSource个数。
	 * @return
	 */
	public NativeLong AVerGetVideoSourceSupported(Pointer pCaptureObject, Pointer pSupported, IntByReference ibrNum);

	/**
	 * 该函数用于设定采集卡VideoSource。
	 *
	 * @param pCaptureObject
	 *            输入采集卡句柄。
	 *
	 * @param iVideoSource
	 *            输入要设定的采集卡VideoSource。
	 * @return
	 */
	public NativeLong AVerSetVideoSource(Pointer pCaptureObject, int iVideoSource);

    /**
     * @Description:
     */
    public NativeLong AVerSetAudioRecordEnabled(Pointer hCaptureObject, boolean bEnabled);

	/**
	 * 该函数用于得到采集卡当前VideoSouce。
	 *
	 * @param pCaptureObject
	 *            输入采集卡句柄。
	 *
	 * @param ibrVideoSource
	 *            输出采集卡当前VideoSource。
	 * @return
	 */
	public NativeLong AVerGetVideoSource(Pointer pCaptureObject, IntByReference ibrVideoSource);

	/**
	 * 该函数用于枚举采集卡支持的AudioSource。
	 *
	 * @param pCaptureObject
	 *            输入采集卡句柄。
	 *
	 * @param iVideoSource
	 *            输入选定的VideoSource。
	 *
	 * @param ibrSupported
	 *            输出支持的AudioSource。
	 * @return
	 */
	public NativeLong AVerGetAudioSourceSupportedEx(Pointer pCaptureObject, int iVideoSource,
                                                    IntByReference ibrSupported);

	/**
	 * 该函数用于设定采集卡AudioSource。
	 *
	 * @param pCaptureObject
	 *            输入采集卡句柄。
	 *
	 * @param iAudioSource
	 *            输入要设定的采集卡AudioSource。
	 * @return
	 */
	public NativeLong AVerSetAudioSource(Pointer pCaptureObject, int iAudioSource);

	/**
	 * 该函数用于得到采集卡当前AudioSouce。
	 *
	 * @param pCaptureObject
	 *            输入采集卡句柄。
	 *
	 * @param ibrAudioSource
	 *            输出采集卡当前AudioSource。
	 * @return
	 */
	public NativeLong AVerGetAudioSource(Pointer pCaptureObject, IntByReference ibrAudioSource);

	/**
	 * 该函数用于设定采集卡VideoFormat。
	 *
	 * @param pCaptureObject
	 *            输入采集卡句柄。
	 *
	 * @param iVideoFormat
	 *            输入要设定的采集卡VideoFormat。
	 * @return
	 */
	public NativeLong AVerSetVideoFormat(Pointer pCaptureObject, int iVideoFormat);

	/**
	 * 该函数用于得到采集卡当前VideoFormat。
	 *
	 * @param pCaptureObject
	 *            输入采集卡句柄。
	 *
	 * @param ibrVideoFormat
	 *            输出采集卡当前VideoFormat。
	 * @return
	 */
	public NativeLong AVerGetVideoFormat(Pointer pCaptureObject, IntByReference ibrVideoFormat);

	/**
	 * 该函数用于得到采集卡支持的Resolution范围。
	 *
	 * @param pCaptureObject
	 *            输入采集卡句柄。
	 *
	 * @param iVideoSource
	 *            输入采集卡VideoSource。
	 *
	 * @param iVideoFormat
	 *            输入采集卡VideoFormat。
	 *
	 * @param pResolutionRangeInfo
	 *            输出采集卡ResolutionRangeInfo。
	 * @return
	 */
	public NativeLong AVerGetVideoResolutionRangeSupported(Pointer pCaptureObject, int iVideoSource, int iVideoFormat,
                                                           RESOLUTION_RANGE_INFO.ByReference pResolutionRangeInfo);

	/**
	 * 该函数用于枚举采集卡支持的Resolution。
	 *
	 * @param pCaptureObject
	 *            输入采集卡句柄。
	 *
	 * @param iVideoSource
	 *            输入采集卡VideoSource。
	 *
	 * @param iVideoFormat
	 *            输入采集卡VideoFormat。
	 *
	 * @param pSupported
	 *            输出采集卡支持的Resolution列表。
	 *
	 * @param ibrNum
	 *            输出采集卡支持的Resolution数量。
	 *
	 * @return
	 */
	public NativeLong AVerGetVideoResolutionSupported(Pointer pCaptureObject, int iVideoSource, int iVideoFormat,
                                                      Pointer pSupported, IntByReference ibrNum);

	/**
	 * 该函数用于设定采集卡VideoResolution。
	 *
	 * @param pCaptureObject
	 *            输入采集卡句柄。
	 *
	 * @param VideoResolution
	 *            输入要设定的采集卡VideoResolution。
	 * @return
	 */
	public NativeLong AVerSetVideoResolutionEx(Pointer pCaptureObject, VIDEO_RESOLUTION.ByReference pVideoResolution);

	/**
	 * 该函数用于得到采集卡当前VideoResolution。
	 *
	 * @param pCaptureObject
	 *            输入采集卡句柄。
	 *
	 * @param VideoResolution
	 *            输出采集卡当前VideoResolution。
	 * @return
	 */
	public NativeLong AVerGetVideoResolutionEx(Pointer pCaptureObject, VIDEO_RESOLUTION.ByReference pVideoResolution);

	/**
	 * 该函数用于得到采集卡支持的Resolution范围。
	 *
	 * @param pCaptureObject
	 *            输入采集卡句柄。
	 *
	 * @param iVideoSource
	 *            输入采集卡VideoSource。
	 *
	 * @param iVideoFormat
	 *            输入采集卡VideoFormat。
	 *
	 * @param iWidth
	 *            输入视频Width。
	 *
	 * @param iHeight
	 *            输入视频Height。
	 *
	 * @param pFrameRateRangeInfo
	 *            输出采集卡FrameRateRangeInfo。
	 * @return
	 */
	public NativeLong AVerGetVideoInputFrameRateRangeSupported(Pointer pCaptureObject, int iVideoSource,
                                                               int iVideoFormat, int iWidth, int iHeight, FRAMERATE_RANGE_INFO.ByReference pFrameRateRangeInfo);

	/**
	 * 该函数用于枚举采集卡支持的FrameRate。
	 *
	 * @param pCaptureObject
	 *            输入采集卡句柄。
	 *
	 * @param iVideoSource
	 *            输入采集卡VideoSource。
	 *
	 * @param iVideoFormat
	 *            输入采集卡VideoFormat。
	 *
	 * @param iVideoResolution
	 *            输入采集卡Resolution。
	 *
	 * @param pSupported
	 *            输出采集卡支持的FrameRate列表。
	 *
	 * @param ibrNum
	 *            输出采集卡支持的FrameRate数量。
	 *
	 * @return
	 */
	public NativeLong AVerGetVideoInputFrameRateSupportedEx(Pointer pCaptureObject, int iVideoSource, int iVideoFormat,
                                                            int iVideoResolution, Pointer pSupported, IntByReference ibrNum);

	/**
	 * 该函数用于设定采集卡FrameRate。
	 *
	 * @param pCaptureObject
	 *            输入采集卡句柄。
	 *
	 * @param iFrameRate
	 *            输入要设定的采集卡FrameRate。
	 * @return
	 */
	public NativeLong AVerSetVideoInputFrameRate(Pointer pCaptureObject, int iFrameRate);

	//>>
    /**
     * @Description: 开启预览
     */
    public NativeLong AVerStart3DPreview(Pointer hCaptureCH1Object, Pointer fillWithNull, HWND hWnd, PointerByReference ph3DObject, Pointer sz3DConfigFilePath);
    //<<

	/**
	 * 该函数用于得到采集卡当前FrameRate。
	 *
	 * @param pCaptureObject
	 *            输入采集卡句柄。
	 *
	 * @param ibrFrameRate
	 *            输出采集卡当前FrameRate。
	 * @return
	 */
	public NativeLong AVerGetVideoInputFrameRate(Pointer pCaptureObject, IntByReference ibrFrameRate);

	/**
	 * 该函数用于启动采集卡。
	 *
	 * @param pCaptureObject
	 *            输入采集卡句柄。
	 * @return
	 */
	public NativeLong AVerStartStreaming(Pointer pCaptureObject);

	/**
	 * 该函数用于停止采集卡。
	 *
	 * @param pCaptureObject
	 *            输入采集卡句柄。
	 * @return
	 */
	public NativeLong AVerStopStreaming(Pointer pCaptureObject);
    // *pAudioInfo);
    // LONG WINAPI AVerGetVideoInfo(HANDLE hCaptureObject, INPUT_VIDEO_INFO
    // *pVideoInfo);
    // LONG WINAPI AVerGetMacroVisionMode(HANDLE hCaptureObject, DWORD
    // LONG WINAPI AVerGetAudioInfo(HANDLE hCaptureObject, INPUT_AUDIO_INFO
    // *pdwMode);
	// LONG WINAPI AVerGetSignalPresence(HANDLE hCaptureObject, BOOL
	// *pbSignalPresence);

	// Preview Control
	/**
	 * 该函数用于停止采集卡。
	 *
	 * @param pCaptureObject
	 *            输入采集卡句柄。
	 *
	 * @param rectVideoWnd
	 *            输入采集视频窗口大小。
	 * @return
	 */
	public NativeLong AVerSetVideoWindowPosition(Pointer pCaptureObject, RECT.ByValue rectVideoWnd);

	/**
	 * 该函数用重绘采集窗口。
	 *
	 * @param pCaptureObject
	 *            输入采集卡句柄。
	 * @return
	 */
	public NativeLong AVerRepaintVideo(Pointer pCaptureObject);

	/**
	 * 该函数用于设定采集卡Render。
	 *
	 * @param pCaptureObject
	 *            输入采集卡句柄。
	 *
	 * @param dwVideoRenderer
	 *            输入采集Render。
	 * @return
	 */
	public NativeLong AVerSetVideoRenderer(Pointer pCaptureObject, int dwVideoRenderer);

	/**
	 * 该函数用于得到采集卡Render。
	 *
	 * @param pCaptureObject
	 *            输入采集卡句柄。
	 *
	 * @param pdwVideoRenderer
	 *            输出采集Render。
	 * @return
	 */
	public NativeLong AVerSetVideoRenderer(Pointer pCaptureObject, IntByReference pdwVideoRenderer);



	 // Capture Image
	 public NativeLong AVerCaptureSingleImage(Pointer pCaptureObject, CAPTURE_SINGLE_IMAGE_INFO.ByReference CaptureSingleImageInfo);

	 // Record
	 public NativeLong  AVerStartRecordFile(Pointer pCaptureObject, PointerByReference pbrRecordObject, Pointer szRecordConfigFilePath);
	 public NativeLong  AVerStopRecordFile(Pointer pRecordObject);

	//
	// // Event Callback

	/**
	 * 该函数用于停止采集卡。
	 *
	 * @param pCaptureObject
	 *            输入采集卡句柄。
	 *
	 * @param lpCallback
	 *            输入回调函数的指针。
	 *
	 * @param dwOptions
	 *            输入必须是1。
	 *
	 * @param lpUserData
	 *            指定一使用者自定义的数据，该数据会在回调函数中被回传给使用者。
	 * @return
	 */
	public NativeLong AVerSetEventCallback(Pointer pCaptureObject, NOTIFYEVENTCALLBACK lpCallback, int dwOptions,
                                           Pointer lpUserData);

}
