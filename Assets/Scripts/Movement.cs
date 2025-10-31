using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.InputSystem;

public class Movement : MonoBehaviour
{
    [Header("Camera Settings")]
    [SerializeField] Transform playerCamera; // Reference to the camera
    [SerializeField][Range(0f, 0.5f)] float mouseSmoothTime = 0.03f; // Smooth mouse movement
    [SerializeField] float mouseSensitivity = 3.5f; // Mouse look sensitivity
    [SerializeField] bool cursorLock = true; // Lock cursor to center

    [Header("Movement Settings")]
    [SerializeField] float speed = 6f; // Movement speed
    [SerializeField][Range(0f, 0.5f)] float moveSmoothTime = 0.3f; // Smooth player movement
    [SerializeField] float gravity = -15f; // Gravity applied to player
    [SerializeField] Transform groundCheck; // Position to check if grounded
    [SerializeField] LayerMask ground; // Layer considered as ground

    [Header("Jump Settings")]
    public float jumpHeight = 0.5f; // Max jump height in meters

    private float velocityY; // Vertical velocity
    private bool isGrounded; // Is player touching the ground

    private float cameraCap; // Up/down rotation clamp
    private Vector2 currentMouseDelta; // Smoothed mouse delta
    private Vector2 currentMouseDeltaVelocity; // Helper for smoothing

    private CharacterController controller; // Reference to CharacterController
    private Vector2 currentDir; // Smoothed movement direction
    private Vector2 currentDirVelocity; // Helper for movement smoothing

    void Start()
    {
        controller = GetComponent<CharacterController>();

        if (cursorLock)
        {
            Cursor.lockState = CursorLockMode.Locked;
            Cursor.visible = false;
        }
    }

    // Update called every frame
    void Update()
    {
        UpdateMouse(); // Handle camera rotation
        UpdateMove();  // Handle movement & jumping
    }

    // Handle mouse look
    void UpdateMouse()
    {
        if (Mouse.current != null)
        {
            Vector2 targetMouseDelta = Mouse.current.delta.ReadValue();
            currentMouseDelta = Vector2.SmoothDamp(currentMouseDelta, targetMouseDelta, ref currentMouseDeltaVelocity, mouseSmoothTime);

            cameraCap -= currentMouseDelta.y * mouseSensitivity;
            cameraCap = Mathf.Clamp(cameraCap, -90f, 90f);

            playerCamera.localEulerAngles = Vector3.right * cameraCap;
            transform.Rotate(Vector3.up * currentMouseDelta.x * mouseSensitivity);
        }
    }

    // Handle player movement and jumping
    void UpdateMove()
    {
        // Check if grounded
        isGrounded = Physics.CheckSphere(groundCheck.position, 0.2f, ground);

        // Reset downward velocity when grounded
        if (isGrounded && velocityY < 0)
            velocityY = -2f;

        // Get movement input
        Vector2 targetDir = Vector2.zero;
        if (Keyboard.current != null)
        {
            targetDir.x = (Keyboard.current.dKey.isPressed ? 1 : 0) + (Keyboard.current.aKey.isPressed ? -1 : 0);
            targetDir.y = (Keyboard.current.wKey.isPressed ? 1 : 0) + (Keyboard.current.sKey.isPressed ? -1 : 0);
            targetDir.Normalize();
        }

        currentDir = Vector2.SmoothDamp(currentDir, targetDir, ref currentDirVelocity, moveSmoothTime);

        // Jump input
        if (Keyboard.current != null && Keyboard.current.spaceKey.wasPressedThisFrame && isGrounded)
        {
            velocityY = Mathf.Sqrt(jumpHeight * -2f * gravity);
        }

        // Apply gravity
        velocityY += gravity * Time.deltaTime;

        // Move player
        Vector3 velocity = (transform.forward * currentDir.y + transform.right * currentDir.x) * speed + Vector3.up * velocityY;
        controller.Move(velocity * Time.deltaTime);
    }
}
