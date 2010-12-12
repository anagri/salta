class Invite < ActiveRecord::Base
  belongs_to :group

  validates_presence_of :group, :email, :token
  validates_length_of :email, :maximum => 100
  validates_uniqueness_of :token
  validates_format_of :email, :with => /^([^@\s]+)@((?:[-a-z0-9]+\.)+[a-z]{2,})$/i
  before_validation(:on => :create) {
    self.token = rand(36**16).to_s(36) if self.new_record? && self.token.nil?
  }

  def inactive?
    !self.active
  end

  def expired?
    self.created_at < 2.days.ago
  end
end