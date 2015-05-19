#define WIN32_LEAN_AND_MEAN
// stops windows.h dragging is loads of unwanted stuff
#include <windows.h>

#define internal static
#define local_persist static
#define global_variable static


global_variable bool running;
global_variable BITMAPINFO BitmapInfo; 
global_variable void* BitmapMemory;
global_variable HBITMAP BitmapHandle;
global_variable HDC BitmapDeviceContext;
global_variable HDC DeviceContext;

const char achTitle      [] = "Example #1";
const char achWindowClass[] = "ExampleNumberOne";

internal void ResizeDIBSection(int Width,int Height)
{
        //bulletproof this
        //free dibsection
        if(BitmapHandle){
            DeleteObject(BitmapHandle);
        } 
        if (!BitmapDeviceContext)
        {
            BitmapDeviceContext = CreateCompatibleDC(0);
        }
        BITMAPINFO BitmapInfo;
        BitmapInfo.bmiHeader.biSize = sizeof(BitmapInfo.bmiHeader);
        BitmapInfo.bmiHeader.biWidth = Width;
        BitmapInfo.bmiHeader.biHeight = Height;
        BitmapInfo.bmiHeader.biPlanes = 1;
        BitmapInfo.bmiHeader.biBitCount = 32;
        BitmapInfo.bmiHeader.biCompression = BI_RGB;

        HBITMAP BitmapHandle = CreateDIBSection(
             DeviceContext, &BitmapInfo, DIB_RGB_COLORS,
             &BitmapMemory,
             0,
             0);
}

internal void Win32UpdateWindw(HDC DeviceContext, int X, int Y, int Width, int Height)
{
       StretchDIBits(DeviceContext, X, Y,
                        Width, Height, X, Y,
                        Width, Height, BitmapMemory, 
                        &BitmapInfo, DIB_RGB_COLORS, 
                        SRCCOPY); 
}

LRESULT CALLBACK WndProcedure(HWND hWnd, UINT uMsg, WPARAM wParam, LPARAM lParam);
ATOM MyRegisterClass(HINSTANCE hInstance);
BOOL InitInstance(HINSTANCE hInstance, int nCmdShow);

int WINAPI WinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance, LPSTR lpCmdLine,
                   int nCmdShow)
{
    MyRegisterClass(hInstance);

    if(!InitInstance(hInstance, nCmdShow))
    {
        return FALSE;
    }
    MSG msg = {0};
    while(GetMessage(&msg, NULL, 0, 0))
    {
        TranslateMessage(&msg);
        DispatchMessage(&msg);
    }
    return msg.wParam;
}

LRESULT CALLBACK WndProcedure(HWND hWnd, UINT Msg, WPARAM wParam, LPARAM lParam)
{
    switch(Msg)
    {
    case WM_CLOSE:
    {
        running = false;
        PostQuitMessage(0);
        break;
    }
    case WM_DESTROY:
    	running = false;
        PostQuitMessage(WM_QUIT);
        break;
    case WM_PAINT:
    {
    	PAINTSTRUCT Paint;
    	HDC DeviceContext = BeginPaint(hWnd,&Paint);
    	int X = Paint.rcPaint.left;
    	int Y = Paint.rcPaint.top;
    	int Height = Paint.rcPaint.bottom - Paint.rcPaint.top;
    	int Width = Paint.rcPaint.right - Paint.rcPaint.left;
    	Win32UpdateWindw(DeviceContext,X,Y,Width,Height);
    	local_persist DWORD Operation = WHITENESS;
    	PatBlt(DeviceContext,X,Y,Width,Height,Operation);

    	if (Operation == WHITENESS){
    		Operation = BLACKNESS;
    	}
    	 else 
    	 {
    	 	Operation = WHITENESS;
    	 }
    	EndPaint(hWnd,&Paint);
    }
    case WM_SIZE:
    {
    	RECT ClientRect;
    	GetClientRect(hWnd, &ClientRect); 
    	int Width = ClientRect.right - ClientRect.left;
    	int Height	= ClientRect.bottom - ClientRect.top;

    	ResizeDIBSection(Width, Height);
    }
    default:
        return DefWindowProc(hWnd, Msg, wParam, lParam);
    }
    return 0;
}

ATOM
MyRegisterClass(HINSTANCE hInstance)
{
    WNDCLASSEX wcex = {0};

    /*HWND WindowHandle = CreateWindowExA(
    									0,
    									wcex.lpszClassName,
    									"Sample Space",
    									WS_OVERLAPPEDWINDOW|WS_VISIBLE,
    									CW_USEDEFAULT,
    									CW_USEDEFAULT,
    									CW_USEDEFAULT,
    									CW_USEDEFAULT,
    									0,
    									0,
    									hInstance,
    									0);
    {
    	if(WindowHandle){
    		for(;;)
    		{
    			MSG Message;
    			BOOL MessageResult = GetMessageA(&Message,0,0,0);
    			if (MessageResult > 0)
    			{
    				TranslateMessage(&Message);
    				DispatchMessage(&Message);
    			}
    			else
    			{
    				break;
    			}
    		}
    	}
    	else 
    	{

    	}
    }
    */

    wcex.cbSize        = sizeof(WNDCLASSEX);
    wcex.style         = CS_HREDRAW | CS_VREDRAW;
    wcex.lpfnWndProc   = WndProcedure;
    wcex.hInstance     = hInstance;
    wcex.hCursor       = LoadCursor(NULL, IDC_ARROW);
    wcex.hbrBackground = (HBRUSH)(COLOR_WINDOW + 1);
    wcex.lpszClassName = achWindowClass;
    return RegisterClassEx(&wcex);
}
BOOL
InitInstance(HINSTANCE hInstance, int nCmdShow)
{
    HWND hWnd = CreateWindow(achWindowClass,
                             achTitle,
                             WS_OVERLAPPEDWINDOW,
                             CW_USEDEFAULT,
                             0,
                             CW_USEDEFAULT,
                             0,
                             NULL,
                             NULL,
                             hInstance,
                             NULL);

    if(NULL == hWnd)
    {
        return FALSE;
    }

    ShowWindow(hWnd, nCmdShow);
    UpdateWindow(hWnd);

    return TRUE;
}


/*

#include <windows.h>

class Simple{
public:
		LRESULT CALLBACK 
		CallWindowProc(
	    	WNDPROC lpPrevWndFunc,
	    	HWND Window,
	    	UINT Messge,
	    	WPARAM WParam,
	    	LPARAM LParam
	){
	
		LRESULT Result = 0;

		
		switch(Message){
			case WM_SIZE:
			{
				printf("%s\n", WM_SIZE);
			} break;
			case WM_DESTROY:
			{
				printf("%s\n", WM_DESTROY);
			} break;
			case WM_CLOSE:
			{
				printf("%s\n", WM_CLOSe);
			} break;
			case WM_ACTIVATEAPP:
			{
				printf("%s\n", WM_ACTIVEATEAPP);
			} break;
			default:
			{
				Result = ;
				//printf("%s\n",default);
			}
			break;
		}

		return(Result);
	}
};



int CALLBACK WinMain( 
				HINSTANCE Instance,
				HINSTANCE PrevInstance,
				LPSTR CommandLine, 
				int ShowCode)
{
	Simple *simple = new Simple();
	WNDCLASS WindowClass = {}; 
	
	WindowClass.style = CS_OWNDC | CS_HREDRAW | CS_VREDRAW;
	WindowClass.lpfnWndProc = simple->CallWindowProc;
	WindowClass.hInstance  = Instance;
	//WindowClass.hIcon;
  	WindowClass.lpszClassName - "ProjectWindowClass";


	return 0;
}

*/