package top

import basic._
import chisel3._

/**
 * A top level to wire FPGA buttons and LEDs
 * to the ALU input and output.
 */
class AluTop extends Module {
  val io = IO(new Bundle {
    val sw = Input(UInt(10.W))
    val led = Output(UInt(10.W))
  })

  val alu = Module(new Alu())

  // Map switches to the ALU input ports
  alu.io.fn := io.sw(1, 0)
  alu.io.a := io.sw(5, 2)
  alu.io.b := io.sw(9, 6)

  // And the result to the LEDs (with 0 extension)
  io.led := alu.io.result
}

// Generate the Verilog code
object TopMain extends App {
  println("Generating the ALU hardware")
  (new chisel3.stage.ChiselStage).emitVerilog(new AluTop(), Array("--target-dir", "generated"))

}
